<?php

namespace Database\Seeders;

use App\Models\Multa;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class MultaSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        foreach ([
            ['M-001', '1234567', 'Ana Pérez', 'Exceso de velocidad', 'Av. 6 de Agosto', 300, 'pendiente'],
            ['M-002', '2345678', 'Luis Rojas', 'Estacionamiento prohibido', 'Calle Bolívar', 150, 'pagada'],
            ['M-003', '3456789', 'María López', 'Luz roja', 'Av. Busch', 500, 'anulada'],
        ] as [$codigo, $ci, $nombre, $tipo, $lugar, $monto, $estado]) {
            Multa::updateOrCreate(['codigo' => $codigo], [
                'ci_conductor' => $ci, 'nombre_conductor' => $nombre, 'fecha' => now()->toDateString(),
                'tipo_infraccion' => $tipo, 'lugar' => $lugar, 'monto' => $monto, 'estado' => $estado,
            ]);
        }
    }
}
