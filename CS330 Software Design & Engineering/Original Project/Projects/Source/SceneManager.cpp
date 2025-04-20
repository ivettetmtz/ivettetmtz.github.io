///////////////////////////////////////////////////////////////////////////////
// shadermanager.cpp
// ============
// manage the loading and rendering of 3D scenes
//
//  AUTHOR: Brian Battersby - SNHU Instructor / Computer Science
//	Created for CS-330-Computational Graphics and Visualization, Nov. 1st, 2023
///////////////////////////////////////////////////////////////////////////////

#include "SceneManager.h"

#ifndef STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#endif

#include <glm/gtx/transform.hpp>

// declaration of global variables
namespace
{
	const char* g_ModelName = "model";
	const char* g_ColorValueName = "objectColor";
	const char* g_TextureValueName = "objectTexture";
	const char* g_UseTextureName = "bUseTexture";
	const char* g_UseLightingName = "bUseLighting";
}

/***********************************************************
 *  SceneManager()
 *
 *  The constructor for the class
 ***********************************************************/
SceneManager::SceneManager(ShaderManager* pShaderManager)
{
	m_pShaderManager = pShaderManager;
	m_basicMeshes = new ShapeMeshes();
}

/***********************************************************
 *  ~SceneManager()
 *
 *  The destructor for the class
 ***********************************************************/
SceneManager::~SceneManager()
{
	m_pShaderManager = NULL;
	delete m_basicMeshes;
	m_basicMeshes = NULL;
}

/***********************************************************
 *  CreateGLTexture()
 *
 *  This method is used for loading textures from image files,
 *  configuring the texture mapping parameters in OpenGL,
 *  generating the mipmaps, and loading the read texture into
 *  the next available texture slot in memory.
 ***********************************************************/
bool SceneManager::CreateGLTexture(const char* filename, std::string tag)
{
	int width = 0;
	int height = 0;
	int colorChannels = 0;
	GLuint textureID = 0;

	// indicate to always flip images vertically when loaded
	stbi_set_flip_vertically_on_load(true);

	// try to parse the image data from the specified image file
	unsigned char* image = stbi_load(
		filename,
		&width,
		&height,
		&colorChannels,
		0);

	// if the image was successfully read from the image file
	if (image)
	{
		std::cout << "Successfully loaded image:" << filename << ", width:" << width << ", height:" << height << ", channels:" << colorChannels << std::endl;

		glGenTextures(1, &textureID);
		glBindTexture(GL_TEXTURE_2D, textureID);

		// set the texture wrapping parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		// set texture filtering parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// if the loaded image is in RGB format
		if (colorChannels == 3)
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
		// if the loaded image is in RGBA format - it supports transparency
		else if (colorChannels == 4)
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
		else
		{
			std::cout << "Not implemented to handle image with " << colorChannels << " channels" << std::endl;
			return false;
		}

		// generate the texture mipmaps for mapping textures to lower resolutions
		glGenerateMipmap(GL_TEXTURE_2D);

		// free the image data from local memory
		stbi_image_free(image);
		glBindTexture(GL_TEXTURE_2D, 0); // Unbind the texture

		// register the loaded texture and associate it with the special tag string
		m_textureIDs[m_loadedTextures].ID = textureID;
		m_textureIDs[m_loadedTextures].tag = tag;
		m_loadedTextures++;

		return true;
	}

	std::cout << "Could not load image:" << filename << std::endl;

	// Error loading the image
	return false;
}

/***********************************************************
 *  BindGLTextures()
 *
 *  This method is used for binding the loaded textures to
 *  OpenGL texture memory slots.  There are up to 16 slots.
 ***********************************************************/
void SceneManager::BindGLTextures()
{
	for (int i = 0; i < m_loadedTextures; i++)
	{
		// bind textures on corresponding texture units
		glActiveTexture(GL_TEXTURE0 + i);
		glBindTexture(GL_TEXTURE_2D, m_textureIDs[i].ID);
	}
}

/***********************************************************
 *  DestroyGLTextures()
 *
 *  This method is used for freeing the memory in all the
 *  used texture memory slots.
 ***********************************************************/
void SceneManager::DestroyGLTextures()
{
	for (int i = 0; i < m_loadedTextures; i++)
	{
		glGenTextures(1, &m_textureIDs[i].ID);
	}
}

/***********************************************************
 *  FindTextureID()
 *
 *  This method is used for getting an ID for the previously
 *  loaded texture bitmap associated with the passed in tag.
 ***********************************************************/
int SceneManager::FindTextureID(std::string tag)
{
	int textureID = -1;
	int index = 0;
	bool bFound = false;

	while ((index < m_loadedTextures) && (bFound == false))
	{
		if (m_textureIDs[index].tag.compare(tag) == 0)
		{
			textureID = m_textureIDs[index].ID;
			bFound = true;
		}
		else
			index++;
	}

	return(textureID);
}

/***********************************************************
 *  FindTextureSlot()
 *
 *  This method is used for getting a slot index for the previously
 *  loaded texture bitmap associated with the passed in tag.
 ***********************************************************/
int SceneManager::FindTextureSlot(std::string tag)
{
	int textureSlot = -1;
	int index = 0;
	bool bFound = false;

	while ((index < m_loadedTextures) && (bFound == false))
	{
		if (m_textureIDs[index].tag.compare(tag) == 0)
		{
			textureSlot = index;
			bFound = true;
		}
		else
			index++;
	}

	return(textureSlot);
}

/***********************************************************
 *  FindMaterial()
 *
 *  This method is used for getting a material from the previously
 *  defined materials list that is associated with the passed in tag.
 ***********************************************************/
bool SceneManager::FindMaterial(std::string tag, OBJECT_MATERIAL& material)
{
	if (m_objectMaterials.size() == 0)
	{
		return(false);
	}

	int index = 0;
	bool bFound = false;
	while ((index < m_objectMaterials.size()) && (bFound == false))
	{
		if (m_objectMaterials[index].tag.compare(tag) == 0)
		{
			bFound = true;
			material.ambientColor = m_objectMaterials[index].ambientColor;
			material.ambientStrength = m_objectMaterials[index].ambientStrength;
			material.diffuseColor = m_objectMaterials[index].diffuseColor;
			material.specularColor = m_objectMaterials[index].specularColor;
			material.shininess = m_objectMaterials[index].shininess;
		}
		else
		{
			index++;
		}
	}

	return(true);
}

/***********************************************************
 *  SetTransformations()
 *
 *  This method is used for setting the transform buffer
 *  using the passed in transformation values.
 ***********************************************************/
void SceneManager::SetTransformations(
	glm::vec3 scaleXYZ,
	float XrotationDegrees,
	float YrotationDegrees,
	float ZrotationDegrees,
	glm::vec3 positionXYZ)
{
	// variables for this method
	glm::mat4 modelView;
	glm::mat4 scale;
	glm::mat4 rotationX;
	glm::mat4 rotationY;
	glm::mat4 rotationZ;
	glm::mat4 translation;

	// set the scale value in the transform buffer
	scale = glm::scale(scaleXYZ);
	// set the rotation values in the transform buffer
	rotationX = glm::rotate(glm::radians(XrotationDegrees), glm::vec3(1.0f, 0.0f, 0.0f));
	rotationY = glm::rotate(glm::radians(YrotationDegrees), glm::vec3(0.0f, 1.0f, 0.0f));
	rotationZ = glm::rotate(glm::radians(ZrotationDegrees), glm::vec3(0.0f, 0.0f, 1.0f));
	// set the translation value in the transform buffer
	translation = glm::translate(positionXYZ);

	modelView = translation * rotationX * rotationY * rotationZ * scale;

	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setMat4Value(g_ModelName, modelView);
	}
}

/***********************************************************
 *  SetShaderColor()
 *
 *  This method is used for setting the passed in color
 *  into the shader for the next draw command
 ***********************************************************/
void SceneManager::SetShaderColor(
	float redColorValue,
	float greenColorValue,
	float blueColorValue,
	float alphaValue)
{
	// variables for this method
	glm::vec4 currentColor;

	currentColor.r = redColorValue;
	currentColor.g = greenColorValue;
	currentColor.b = blueColorValue;
	currentColor.a = alphaValue;

	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setIntValue(g_UseTextureName, false);
		m_pShaderManager->setVec4Value(g_ColorValueName, currentColor);
	}
}

/***********************************************************
 *  SetShaderTexture()
 *
 *  This method is used for setting the texture data
 *  associated with the passed in ID into the shader.
 ***********************************************************/
void SceneManager::SetShaderTexture(
	std::string textureTag)
{
	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setIntValue(g_UseTextureName, true);

		int textureID = -1;
		textureID = FindTextureSlot(textureTag);
		m_pShaderManager->setSampler2DValue(g_TextureValueName, textureID);
	}
}

/***********************************************************
 *  SetTextureUVScale()
 *
 *  This method is used for setting the texture UV scale
 *  values into the shader.
 ***********************************************************/
void SceneManager::SetTextureUVScale(float u, float v)
{
	if (NULL != m_pShaderManager)
	{
		m_pShaderManager->setVec2Value("UVscale", glm::vec2(u, v));
	}
}

/***********************************************************
 *  SetShaderMaterial()
 *
 *  This method is used for passing the material values
 *  into the shader.
 ***********************************************************/
void SceneManager::SetShaderMaterial(
	std::string materialTag)
{
	if (m_objectMaterials.size() > 0)
	{
		OBJECT_MATERIAL material;
		bool bReturn = false;

		bReturn = FindMaterial(materialTag, material);
		if (bReturn == true)
		{
			m_pShaderManager->setVec3Value("material.ambientColor", material.ambientColor);
			m_pShaderManager->setFloatValue("material.ambientStrength", material.ambientStrength);
			m_pShaderManager->setVec3Value("material.diffuseColor", material.diffuseColor);
			m_pShaderManager->setVec3Value("material.specularColor", material.specularColor);
			m_pShaderManager->setFloatValue("material.shininess", material.shininess);
		}
	}
}

/**************************************************************/
/*** STUDENTS CAN MODIFY the code in the methods BELOW for  ***/
/*** preparing and rendering their own 3D replicated scenes.***/
/*** Please refer to the code in the OpenGL sample project  ***/
/*** for assistance.                                        ***/
/**************************************************************/

/***********************************************************
  *  LoadSceneTextures()
  *
  *  This method is used for preparing the 3D scene by loading
  *  the shapes, textures in memory to support the 3D scene
  *  rendering
  ***********************************************************/
void SceneManager::LoadSceneTextures()
{
	/*** STUDENTS - add the code BELOW for loading the textures that ***/
	/*** will be used for mapping to objects in the 3D scene. Up to  ***/
	/*** 16 textures can be loaded per scene. Refer to the code in   ***/
	/*** the OpenGL Sample for help.                                 ***/

	//loaded the texture images to be used
	//bool bReturn = false;

	this->CreateGLTexture(
		"../../Utilities/textures/knife_handle.jpg", "wood");

	this->CreateGLTexture(
		"../../Utilities/textures/grass.jpg", "grass");

	this->CreateGLTexture(
		"../../Utilities/textures/Rubber.jpeg", "rubber");

	this->CreateGLTexture(
		"../../Utilities/textures/Pattern.jpeg", "pattern");

	this->CreateGLTexture("../../Utilities/textures/gold.jpg", "gold");

	this->CreateGLTexture("../../Utilities/textures/rusticwood.jpg", "rusticwood");

	this->CreateGLTexture("../../Utilities/textures/stainless.jpg", "stainless");

	this->CreateGLTexture("../../Utilities/textures/stainedglass.jpg", "stained");

	this->CreateGLTexture("../../Utilities/textures/drywall.jpg", "drywall");

	// after the texture image data is loaded into memory, the
	// loaded textures need to be bound to texture slots - there
	// are a total of 16 available slots for scene textures
	BindGLTextures();
}

/***********************************************************
  *  DefineObjectMaterials()
  *
  *  This method is used for configuring the various material
  *  settings for all of the objects within the 3D scene.
  ***********************************************************/
void SceneManager::DefineObjectMaterials()
{
	/*** STUDENTS - add the code BELOW for defining object materials. ***/
	/*** There is no limit to the number of object materials that can ***/
	/*** be defined. Refer to the code in the OpenGL Sample for help  ***/

	OBJECT_MATERIAL woodMaterial;
	woodMaterial.ambientColor = glm::vec3(0.9f, 0.7f, 0.1f);
	woodMaterial.ambientStrength = 0.09f;
	woodMaterial.diffuseColor = glm::vec3(0.3f, 0.5f, 0.4f);
	woodMaterial.specularColor = glm::vec3(0.5f, 0.5f, 0.1f);
	woodMaterial.shininess = 0.3;
	woodMaterial.tag = "wood";

	m_objectMaterials.push_back(woodMaterial);

	OBJECT_MATERIAL clayMaterial;
	clayMaterial.ambientColor = glm::vec3(0.2f, 0.2f, 0.3f);
	clayMaterial.ambientStrength = 2.0f;
	clayMaterial.diffuseColor = glm::vec3(0.1f, 0.1f, 0.1f);
	clayMaterial.specularColor = glm::vec3(1.2f, 0.2f, 0.4f);
	clayMaterial.shininess = 0.5;
	clayMaterial.ambientStrength = 0.1f;
	clayMaterial.tag = "clay";

	m_objectMaterials.push_back(clayMaterial);

	OBJECT_MATERIAL plasticMaterial;
	plasticMaterial.ambientColor = glm::vec3(0.9f, 0.8f, 0.3f);
	plasticMaterial.ambientStrength = 5.0f;
	plasticMaterial.diffuseColor = glm::vec3(0.5f, 0.5f, 0.5f);
	plasticMaterial.specularColor = glm::vec3(1.4f, 0.4f, 0.4f);
	plasticMaterial.shininess = 1.5;
	plasticMaterial.tag = "plastic";

	m_objectMaterials.push_back(plasticMaterial);

	OBJECT_MATERIAL normal_Material_shade;
	normal_Material_shade.ambientColor = glm::vec3(0.9f, 0.8f, 0.3f);
	normal_Material_shade.diffuseColor = glm::vec3(0.5f);
	normal_Material_shade.specularColor = glm::vec3(0.1f, 0.1f, 0.2f);
	normal_Material_shade.shininess = 1.0f;
	normal_Material_shade.ambientStrength = 0.1f;
	normal_Material_shade.tag = "LightingMaterial";

	m_objectMaterials.push_back(normal_Material_shade);

	OBJECT_MATERIAL goldMaterial;
	goldMaterial.ambientColor = glm::vec3(0.9f, 0.8f, 0.3f);
	goldMaterial.diffuseColor = glm::vec3(0.4f);
	goldMaterial.specularColor = glm::vec3(0.1f, 0.5f, 0.2f);
	goldMaterial.shininess = 1.0f;
	goldMaterial.ambientStrength = 0.1f;
	goldMaterial.tag = "gold";

	m_objectMaterials.push_back(goldMaterial);

	OBJECT_MATERIAL rusticwoodMaterial;
	rusticwoodMaterial.ambientColor = glm::vec3(0.9f, 0.7f, 0.1f);
	rusticwoodMaterial.diffuseColor = glm::vec3(0.4f);
	rusticwoodMaterial.specularColor = glm::vec3(0.5f, 0.5f, 0.1f);
	rusticwoodMaterial.shininess = 0.3f;
	rusticwoodMaterial.ambientStrength = 0.1f;
	rusticwoodMaterial.tag = "rusticwood";

	m_objectMaterials.push_back(rusticwoodMaterial);

	OBJECT_MATERIAL stainlessMaterial;
	stainlessMaterial.ambientColor = glm::vec3(0.9f, 0.8f, 0.3f);
	stainlessMaterial.diffuseColor = glm::vec3(0.5f);
	stainlessMaterial.specularColor = glm::vec3(0.1f, 0.1f, 0.2f);
	stainlessMaterial.shininess = 1.0f;
	stainlessMaterial.ambientStrength = 0.1f;
	stainlessMaterial.tag = "stainless";

	m_objectMaterials.push_back(stainlessMaterial);

	OBJECT_MATERIAL stainedMaterial;
	stainedMaterial.ambientColor = glm::vec3(0.9f, 0.8f, 0.3f);
	stainedMaterial.diffuseColor = glm::vec3(0.5f);
	stainedMaterial.specularColor = glm::vec3(0.1f, 0.1f, 0.2f);
	stainedMaterial.shininess = 1.0f;
	stainedMaterial.ambientStrength = 0.1f;
	stainedMaterial.tag = "stained";

	m_objectMaterials.push_back(stainedMaterial);

}

/***********************************************************
 *  SetupSceneLights()
 *
 *  This method is called to add and configure the light
 *  sources for the 3D scene.  There are up to 4 light sources.
 ***********************************************************/
void SceneManager::SetupSceneLights()
{
	// this line of code is NEEDED for telling the shaders to render 
	// the 3D scene with custom lighting, if no light sources have
	// been added then the display window will be black - to use the 
	// default OpenGL lighting then comment out the following line
	//m_pShaderManager->setBoolValue(g_UseLightingName, true);

	m_pShaderManager->use();

	m_pShaderManager->setVec3Value("lightSources[0].position", glm::vec3(3.0f, 14.0f, 0.0f));
	m_pShaderManager->setVec3Value("lightSources[0].ambientColor", glm::vec3(0.2f));
	m_pShaderManager->setVec3Value("lightSources[0].diffuseColor", glm::vec3(0.7f, 0.4f, 0.4f));
	m_pShaderManager->setVec3Value("lightSources[0].specularColor", glm::vec3(0.3f));

	m_pShaderManager->setFloatValue("lightSources[0].focalStrength", 64.0f);
	m_pShaderManager->setFloatValue("lightSources[0].specularIntensity", 5.0f);

	//LightSource2
	m_pShaderManager->setVec3Value("lightSources[1].position", glm::vec3(3.0f, 10.0f, -24.0f));
	m_pShaderManager->setVec3Value("lightSources[1].ambientColor", glm::vec3(0.2f, 0.1f, 0.0f));
	m_pShaderManager->setVec3Value("lightSources[1].diffuseColor", glm::vec3(0.4f, 0.4f, 0.5f));
	m_pShaderManager->setVec3Value("lightSources[1].specularColor", glm::vec3(0.2f, 0.3f, 0.3f));

	m_pShaderManager->setFloatValue("lightSources[1].focalStrength", 64.0f);
	m_pShaderManager->setFloatValue("lightSources[1].specularIntensity", 5.0f);

	m_pShaderManager->setBoolValue("bUseLighting", true);
}

/***********************************************************
 *  PrepareScene()
 *
 *  This method is used for preparing the 3D scene by loading
 *  the shapes, textures in memory to support the 3D scene
 *  rendering
 ***********************************************************/
void SceneManager::PrepareScene()
{
	// define the materials that will be used for the objects
	// in the 3D scene
	DefineObjectMaterials();

	// add and defile the light sources for the 3D scene
	SetupSceneLights();

	// load the textures for the 3D scene
	LoadSceneTextures();

	// only one instance of a particular mesh needs to be
	// loaded in memory no matter how many times it is drawn
	// in the rendered 3D scene

	// added the needed shapes: cylinder, sphere, and tapered cylinder into code
	m_basicMeshes->LoadCylinderMesh();
	m_basicMeshes->LoadSphereMesh();
	m_basicMeshes->LoadTaperedCylinderMesh();
	m_basicMeshes->LoadPlaneMesh();
	m_basicMeshes->LoadBoxMesh();
	m_basicMeshes->LoadTorusMesh();
}

/***********************************************************
 *  RenderScene()
 *
 *  This method is used for rendering the 3D scene by
 *  transforming and drawing the basic 3D shapes
 ***********************************************************/
void SceneManager::RenderScene()
{
	// declare the variables for the transformations
	glm::vec3 scaleXYZ;
	float XrotationDegrees = 0.0f;
	float YrotationDegrees = 0.0f;
	float ZrotationDegrees = 0.0f;
	glm::vec3 positionXYZ;

	/*** Set needed transformations before drawing the basic mesh.  ***/
	/*** This same ordering of code should be used for transforming ***/
	/*** and drawing all the basic 3D shapes.						***/
	/******************************************************************/
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(15.0f, 0.5f, 15.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.0f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	SetShaderColor(1, 1, 1, 1);

	// Used grass texture to match a outside scenery
	SetShaderTexture("grass");
	//SetTextureUVScale(1.0, 1.0);
	//SetShaderMaterial("LightingMaterial");

	// draw the mesh with transformation values
	m_basicMeshes->DrawPlaneMesh();
	/****************************************************************/

	// creating Cylinder
	// set the XYZ scale for the mesh
	// changed scaling to match the size of the ring toss pole
	scaleXYZ = glm::vec3(2.5f, 0.5f, 2.5f);

	// set the XYZ rotation for the mesh
	// rotation was centered
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.0f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(0.286, 0.658, 0.164, 0.286);

	// Tiled a rubber texture to represent a plastic toy
	SetShaderTexture("rubber");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("clay"); 

	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh();
	/****************************************************************/

	// creating the Sphere 
	// set the XYZ scale for the mesh
	// changed scaling to match the size of the image
	scaleXYZ = glm::vec3(0.5f, 0.4f, 0.5f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	// position of sphere is on top of tapered cylinder to use as a topper
	positionXYZ = glm::vec3(0.0f, 8.3f, -0.7f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1, 0.992, 0.333, 1);

	// Pattern was given to the top of the ring toss game to add more detail
	SetShaderTexture("pattern");
	//SetTextureUVScale(2.0, 2.0);
	SetShaderMaterial("plastic");

	// draw the mesh with transformation values
	m_basicMeshes->DrawSphereMesh();
	/****************************************************************/

	// creating the Tapered Cylinder 
	// set the XYZ scale for the mesh
	// changed scaling to stretch the tapered cylinder and match the pole 2D image
	scaleXYZ = glm::vec3(0.8f, 7.5f, 0.8f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = -5.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	// tapered cylinder is set on top of the small green cylinder base 
	positionXYZ = glm::vec3(0.0f, 0.5f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	// chose one of the colors from the image and set it for the cylinder 
	SetShaderColor(0.196, 0.509, 1, 0.196);

	// White wood is most commonly used for the pole to toss the hoops
	SetShaderTexture("wood");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("wood");

	// draw the mesh with transformation values
	m_basicMeshes->DrawTaperedCylinderMesh();

	/****************************************************************/

	// creating the sphere for plastic ball
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(1.0f, 1.0f, 1.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 50.0f;
	YrotationDegrees = -50.0f;
	ZrotationDegrees = -90.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(4.0f, 1.0f, 4.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// Gave the bell ball stained color
	SetShaderTexture("stained");
	//SetTextureUVScale(2.0, 2.0);
	SetShaderMaterial("stained");

	// draw the mesh with transformation values
	m_basicMeshes->DrawHalfSphereMesh();

	/****************************************************************/

	// creating the sphere for plastic ball
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(1.0f, 1.0f, 1.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = -60.0f;
	YrotationDegrees = 20.0f;
	ZrotationDegrees = 45.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(4.0f, 1.0f, 4.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// Gave the bell ball stained color
	SetShaderTexture("drywall");
	//SetTextureUVScale(2.0, 2.0);
	SetShaderMaterial("stained");

	// draw the mesh with transformation values
	m_basicMeshes->DrawHalfSphereMesh();

	/****************************************************************/

	// creating the torus for the hoop
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(1.4f, 1.2f, 0.4f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 90.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.3f, 5.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(0.5, 0.5, 0.5, 1);

	// The hoop mimics the gold/yellow color of the picture
	SetShaderTexture("gold");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("gold");

	// draw the mesh with transformation values
	m_basicMeshes->DrawTorusMesh();

	/****************************************************************/

	// creating one of the torus for the wheels of the skate
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(0.5f, 0.5f, 0.5f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 30.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-5.2f, 0.6f, 0.8f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// Gave the wheels of the skate a rubber black look
	SetShaderTexture("rubber");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("clay");

	// draw the mesh with transformation values
	m_basicMeshes->DrawTorusMesh();
	
	/****************************************************************/

	// creating one of the torus for the wheels of the skate
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(0.5f, 0.5f, 0.5f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 30.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-7.0f, 0.6f, 0.4f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// Gave the wheels of the skate a rubber black look
	SetShaderTexture("rubber");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("clay");

	// draw the mesh with transformation values
	m_basicMeshes->DrawTorusMesh();

	/****************************************************************/

	// creating one of the torus for the wheels of the skate
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(0.5f, 0.5f, 0.5f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 30.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-5.7f, 0.6f, -0.3f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// Gave the wheels of the skate a rubber black look
	SetShaderTexture("rubber");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("clay");

	// draw the mesh with transformation values
	m_basicMeshes->DrawTorusMesh();

	/****************************************************************/

	// creating one of the torus for the wheels of the skate
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(0.5f, 0.5f, 0.5f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 30.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-6.5f, 0.6f, 1.5f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// Gave the wheels of the skate a rubber black look
	SetShaderTexture("rubber");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("clay");

	// draw the mesh with transformation values
	m_basicMeshes->DrawTorusMesh();

	/****************************************************************/

	// creating the steel block holding the wheels
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(2.2f, 0.5f, 1.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 30.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-6.1f, 0.6f, 0.6f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// Gave the steel block holding the wheels a steel look
	SetShaderTexture("stainless");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("stainless");

	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh();

	/****************************************************************/

	// creating Cylinder for wooden perch
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(0.4f, 2.0f, 0.4f);

	// set the XYZ rotation for the mesh
	// rotation was centered
	XrotationDegrees = 90.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = -30.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-6.6f, 1.9f, -0.3f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	// The perch of the bird will be a wooden texture
	SetShaderTexture("rusticwood");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("rusticwood");

	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh();

	/****************************************************************/

	// creating cylinder for screw
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(0.3f, 1.0f, 0.3f);

	// set the XYZ rotation for the mesh
	// rotation was centered
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-6.1f, 0.6f, 0.6f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 1.0, 1.0, 1.0);

	SetShaderTexture("stainless");
	//SetTextureUVScale(1.0, 1.0);
	SetShaderMaterial("stainless");

	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh();
}

