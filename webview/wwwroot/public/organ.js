const urlParams = new URLSearchParams(window.location.search);
var organ = urlParams.get('organ');

if (organ == 'cardiovascular' || organ == 'musculoskeletal') {
	var image = document.getElementById("image");
	image.src = "./" + organ + "/" + organ + ".png";
}

var resourceURL = organ + '/scene.gltf'

var cameraPositionList = {
	'brain': { x: 0, y: 0, z: 500 },
	'respiratory': { x: 0, y: 1, z: 1 },
	'renal': { x: 0, y: 0, z: 500 },
	'liver': { x: 0, y: 0, z: 0.4 },
	'digestive': { x: 0, y: 0, z: 100 },
};
var cameraLookatList = {
	'brain': { x: -1, y: -1, z: -1 },
	'respiratory': { x: -1, y: -1, z: -1 },
	'renal': { x: -1, y: -1, z: -1 },
	'liver': { x: -1, y: -1, z: -1 },
	'digestive': { x: -1, y: -1, z: -1 },
};

var cameraPosition = cameraPositionList[organ];
var cameraLookat = cameraLookatList[organ];

import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader.js';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);

// Light
const frontLight = new THREE.DirectionalLight(0xffffff, 1);
frontLight.position.set(0, 0, 100);
scene.add(frontLight);
const backLight = new THREE.DirectionalLight(0xffffff, 1);
backLight.position.set(0, 0, -100);
scene.add(backLight);

// Background
scene.background = new THREE.Color(0x808080);

// Camera
camera.position.set(cameraPosition.x, cameraPosition.y, cameraPosition.z);
camera.lookAt(cameraLookat.x, cameraLookat.y, cameraLookat.z);
camera.updateMatrixWorld();

// Renderer
const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

// Controls
const controls = new OrbitControls(camera, renderer.domElement);
controls.enableZoom = true;
controls.enableRotate = true;
controls.enablePan = true;
controls.zoomSpeed = 0.5;
controls.rotateSpeed = 0.5;
controls.panSpeed = 0.5;

// Instantiate a loader
const loader = new GLTFLoader();

// Load a glTF resource
loader.load(
	// resource URL
	resourceURL,
	// called when the resource is loaded
	function (gltf) {
		var model = gltf.scene;
		scene.add(model);

		gltf.animations; // Array<THREE.AnimationClip>
		gltf.scene; // THREE.Group
		gltf.scenes; // Array<THREE.Group>
		gltf.cameras; // Array<THREE.Camera>
		gltf.asset; // Object

		function animate() {
			requestAnimationFrame(animate);
			// model.rotation.y += 0.01;
			controls.update();
			renderer.render(scene, camera);
		};

		animate();

	},
	// called while loading is progressing
	function (xhr) {

		console.log((xhr.loaded / xhr.total * 100) + '% loaded');
		showPrompt((xhr.loaded / xhr.total * 100) + '% loaded');

	},
	// called when loading has errors
	function (error) {

		console.log('An error happened');
		showPrompt('An error happened');

	}
);

function showPrompt(message) {
	document.getElementById("prompt").innerHTML = message;
}

function sendMessage(message) {
	JSBridge.showMessageInNative(message);
}