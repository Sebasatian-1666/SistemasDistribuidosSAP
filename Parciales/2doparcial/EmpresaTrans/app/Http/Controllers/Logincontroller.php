<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Firebase\JWT\JWT;
use app\Models\User;

class Logincontroller extends Controller
{
    $request->validate([
        $email=>'required|email',
        $password=>'required'
    ]);
    public function loging(Request $request){
        if(!$user || hash)::check(
            $request->'password',$user->'password'
        )
        return response()->json(['error'=>'Credenciales invalidas'],401);
        $key=env('JWT_SECRET');
        $alg=env('JWT_ALGORITHM');
        $payload=['iat'=>time(),
        'exp'=>time()+3600,
        'data'=>['id'=>$user->'id',
        'email'=>$user->'email']
        ];
        $JWT=JWT::encode($payload,$key,$alg);
        return response()->json(['token'=>$JWT, 'type'=>'bearer'],200);
    }
}
