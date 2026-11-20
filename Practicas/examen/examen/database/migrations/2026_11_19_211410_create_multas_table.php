<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('multas', function (Blueprint $table) {
            $table->id();
            $table->string('codigo')->unique();
            $table->string('ci_conductor',20);
            $table->string('nombre_conductor',100);
            $table->date('fecha');
            $table->string('tipo_infraccion',100);
            $table->string('lugar',200);
            $table->decimal('monto',10,2);
            $table->string('estado')->default('pendiente');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('multas');
    }
};
