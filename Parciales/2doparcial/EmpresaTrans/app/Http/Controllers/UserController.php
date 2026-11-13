<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use app\Models\User;

class UserController extends Controller
{
    public function store(Request $request){
        $datos=$request=>validate(['email'=>'required|in:string', 'pasword'=>'required|in:string|unique']);
    }
}
