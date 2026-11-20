<?php

namespace App\Http\Controllers;

use App\Models\Multa;
use Illuminate\Http\Request;

class MultaController extends Controller
{
    public function store(Request $request)
    {
        $data = $request->validate([
            'codigo' => 'required|string|unique:multas,codigo',
            'ci_conductor' => 'required|string|max:20',
            'nombre_conductor' => 'required|string|max:100',
            'fecha' => 'required|date',
            'tipo_infraccion' => 'required|string|max:100',
            'lugar' => 'required|string|max:200',
            'monto' => 'required|numeric|min:0',
        ]);
        return response()->json(Multa::create($data), 201);
    }

    public function show(string $codigo)
    {
        return response()->json(Multa::where('codigo', $codigo)->firstOrFail());
    }
}
