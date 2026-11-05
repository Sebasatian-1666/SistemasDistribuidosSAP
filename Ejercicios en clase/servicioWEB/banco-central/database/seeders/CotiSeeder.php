<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Coti;

class CotiSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
       $d = [
            ['fec' => '2025-04-04', 'val' => 7.02, 'ofi' => 6.97],
            ['fec' => '2025-04-05', 'val' => 7.05, 'ofi' => 6.97],
            ['fec' => '2025-04-06', 'val' => 7.10, 'ofi' => 6.97],
            ['fec' => '2025-04-07', 'val' => 8.20, 'ofi' => 6.97],
            ['fec' => '2025-04-08', 'val' => 8.30, 'ofi' => 6.97],
            ['fec' => '2025-04-09', 'val' => 8.40, 'ofi' => 6.97],
            ['fec' => '2025-04-10', 'val' => 8.70, 'ofi' => 6.97],
            ['fec' => '2025-04-11', 'val' => 9.00, 'ofi' => 6.97],
            ['fec' => '2025-04-12', 'val' => 9.20, 'ofi' => 6.97],
        ];

        foreach ($d as $i) {
            Coti::create($i);
        }
    }
}
