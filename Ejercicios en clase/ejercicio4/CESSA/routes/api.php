<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

$facturas = [
    ['id' => 1, 'ci' => '1234567', 'nombre' => 'Juan Carlos Perez Gomez', 'servicio' => 'Electricidad', 'monto' => 120.50, 'estado' => 'Pendiente', 'mes' => 'Octubre 2026'],
    ['id' => 2, 'ci' => '7654321', 'nombre' => 'Maria Elena Lopez Martinez', 'servicio' => 'Electricidad', 'monto' => 95.00, 'estado' => 'Pendiente', 'mes' => 'Octubre 2026'],
    ['id' => 3, 'ci' => '1111111', 'nombre' => 'Pedro Rojas Fernandez', 'servicio' => 'Electricidad', 'monto' => 80.25, 'estado' => 'Pagada', 'mes' => 'Septiembre 2026'],
];

Route::get('/facturas/{ci}', function (string $ci) use ($facturas) {
    return response()->json(['exito' => true, 'facturas' => array_values(array_filter($facturas, fn ($factura) => $factura['ci'] === $ci))]);
});

Route::post('/facturas/pagar', function (Request $request) use ($facturas) {
    $factura = collect($facturas)->first(fn ($item) => $item['ci'] === (string) $request->ci && $item['id'] === (int) $request->factura_id);

    if (!$factura) {
        return response()->json(['exito' => false, 'mensaje' => 'Factura no encontrada'], 404);
    }

    if ($factura['estado'] === 'Pagada') {
        return response()->json(['exito' => false, 'mensaje' => 'La factura ya fue pagada']);
    }

    return response()->json(['exito' => true, 'mensaje' => 'Pago realizado correctamente', 'ci' => $factura['ci'], 'factura_id' => $factura['id'], 'servicio' => 'CESSA', 'fecha_pago' => now()->toDateTimeString()]);
});
