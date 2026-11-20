<?php

use App\Http\Controllers\MultaController;
use Illuminate\Support\Facades\Route;

Route::post('/multas', [MultaController::class, 'store']);
Route::get('/multas/{codigo}', [MultaController::class, 'show']);
