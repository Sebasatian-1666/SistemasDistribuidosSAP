// cliente.js

// 1. LA LÍNEA DE CONEXIÓN BASE (A dónde nos conectamos)
const url = "http://127.0.0.1:3000/api/coti";

// --- CLIENTE GET (Buscar) ---
async function probarGet(fec) {
    // 2. LÍNEA DE CONEXIÓN GET: fetch hace la petición HTTP directa al servidor
    let res = await fetch(`${url}/${fec}`);
    let data = await res.json();
    console.log("Respuesta GET:", data);
}

// --- CLIENTE POST (Registrar) ---
async function probarPost(fec, val) {
    // 3. LÍNEAS DE CONEXIÓN POST: Se define el método, las cabeceras y el body (carga útil)
    let conexion = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ fec, val })
    };
    
    // Ejecuta la conexión con la configuración armada
    let res = await fetch(url, conexion);
    let data = await res.json();
    console.log("Respuesta POST:", data);
}

// Ejecutamos las pruebas del cliente (asegúrate de que tu servidor esté corriendo)
probarGet("2025-04-04");
probarPost("2025-04-10", 8.20);