<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Models\Coti;
class CotiController extends Controller
{
    public function registrar($_, array $args) {
        $fec = $args['fec'];
        $val = $args['val'];
        $ofi = 6.97;

        $ex = Coti::where('fec', $fec)->first();
        if ($ex) {
            throw new Exception("Error: Fecha duplicada");
        }

        if ($val <= 0) {
            throw new Exception("Error: Monto invalido");
        }

        return Coti::create(compact('fec', 'val', 'ofi'));
    }
}
