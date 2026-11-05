<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

$facturas = [
    ['id' => 1, 'ci' => '1234567', 'nombre' => 'Juan Carlos Perez Gomez', 'servicio' => 'Agua potable', 'monto' => 75.30, 'estado' => 'Pendiente', 'mes' => 'Octubre 2026'],
    ['id' => 2, 'ci' => '7654321', 'nombre' => 'Maria Elena Lopez Martinez', 'servicio' => 'Agua potable', 'monto' => 62.00, 'estado' => 'Pendiente', 'mes' => 'Octubre 2026'],
    ['id' => 3, 'ci' => '2222222', 'nombre' => 'Ana Lucia Mendoza Vargas', 'servicio' => 'Agua potable', 'monto' => 53.25, 'estado' => 'Pendiente', 'mes' => 'Septiembre 2026'],
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

    return response()->json(['exito' => true, 'mensaje' => 'Pago realizado correctamente', 'ci' => $factura['ci'], 'factura_id' => $factura['id'], 'servicio' => 'ELAPAS', 'fecha_pago' => now()->toDateTimeString()]);
});
