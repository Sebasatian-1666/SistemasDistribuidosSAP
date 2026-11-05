const express = require('express');
const app = express();
app.use(express.json());

// Array dinámico (simula la base de datos)
let bd = [
    { fec: '2025-04-04', val: 7.02, ofi: 6.97 },
    { fec: '2025-04-05', val: 7.05, ofi: 6.97 }
];

// GET: Buscar
app.get('/api/coti/:fec', (req, res) => {
    let c = bd.find(x => x.fec === req.params.fec);
    c ? res.json(c) : res.status(404).json({ error: "No encontrada" });
});

// POST: Registrar
app.post('/api/coti', (req, res) => {
    let { fec, val } = req.body;
    let ofi = 6.97;

    if (bd.find(x => x.fec === fec)) return res.status(400).json({ error: "Fecha duplicada" });
    if (val <= 0) return res.status(400).json({ error: "Monto invalido" });

    let n = { fec, val, ofi };
    bd.push(n); 
    res.json(n);
});

app.listen(3000, () => console.log("REST corriendo en puerto 3000"));