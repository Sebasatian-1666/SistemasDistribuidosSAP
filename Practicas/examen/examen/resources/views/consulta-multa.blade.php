<!doctype html>
<html lang="es">
<head><meta charset="utf-8"><title>Consultar multa</title></head>
<body>
    <h1>Consulta de multa</h1>
    <form method="get" action="/consulta">
        <label>Código <input name="codigo" value="{{ request('codigo') }}" required></label>
        <label>CI <input name="ci" value="{{ request('ci') }}" required></label>
        <button type="submit">Consultar</button>
    </form>
    @if ($error)<p>{{ $error }}</p>@endif
    @if ($multa)
        <p>Código: {{ $multa->codigo }}</p>
        <p>Infracción: {{ $multa->tipo_infraccion }}</p>
        <p>Monto: {{ $multa->monto }}</p>
        <p>Estado: {{ $multa->estado }}</p>
    @endif
</body>
</html>
