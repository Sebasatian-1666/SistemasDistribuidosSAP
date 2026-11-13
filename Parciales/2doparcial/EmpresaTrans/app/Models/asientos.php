<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class asientos extends Model
{
    protected $fillable['salida','numero','CI'];
    use HasFactory;
}
