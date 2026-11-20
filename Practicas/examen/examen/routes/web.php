<?php

use Illuminate\Support\Facades\Route;
use App\Models\Multa;
use Illuminate\Http\Request;

Route::get('/', function () {
    return view('welcome');
});

Route::get('/consulta', function (Request $request) {
    $multa = null;
    $error = null;
    if ($request->filled('codigo') && $request->filled('ci')) {
        $multa = Multa::where('codigo', $request->codigo)
            ->where('ci_conductor', $request->ci)->first();
        if (!$multa) $error = 'No se encontró la multa para esos datos.';
    }
    return view('consulta-multa', compact('multa', 'error'));
});
