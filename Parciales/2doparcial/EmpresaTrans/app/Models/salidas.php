<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class salidas extends Model
{
    protected $fillable=['destino','fecha','hora','bus','precio','capacidad'];
    use HasFactory;
}
