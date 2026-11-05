<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Coti extends Model
{
    /** @use HasFactory<\Database\Factories\CotiFactory> */
    use HasFactory;
    protected $fillable = ['fec','val','ofi'];
}
