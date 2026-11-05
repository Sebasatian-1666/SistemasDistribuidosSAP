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
        Schema::create('cotis', function (Blueprint $table) {
            $table->id();
            $table->date('fec')->unique();
            $table->decimal('val',8,2);
            $table->decimal('ofi',8,2)->default(6.97); 
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('cotis');
    }
};
