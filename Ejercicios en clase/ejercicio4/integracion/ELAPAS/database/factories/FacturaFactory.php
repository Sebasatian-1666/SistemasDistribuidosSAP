<?php

namespace Database\Factories;

use App\Models\Factura;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends Factory<Factura>
 */
class FacturaFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'numero_documento' => $this->faker->unique()->regexify('[0-9]{15}'),
            'nombre_completo' => $this->faker->name(),
            'numero_factura' => $this->faker->unique()->randomNumber(8),
            'anio' => $this->faker->year(),
            'mes' => $this->faker->randomElement(['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio']),
            'total_factura' => $this->faker->randomFloat(2, 0, 9999999.99),
            'estado' => $this->faker->randomElement(['emitida', 'pagada', 'anulada']),
        ];
    }
}
