<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;
use Firebase\JWT\key;
class JwtMiddleware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Closure(\Illuminate\Http\Request): (\Symfony\Component\HttpFoundation\Response)  $next
     */
    public function handle(Request $request, Closure $next): Response
    {
        $header=$request -> header('Authorized');
        if(!$header || string_starts_with('header','bearer')){
            return response()->json(['error'=> 'No autorizado'],403);
            $JWT =substring($header,7);
            try($key=env('JWT_SECRET'){
            $alg=env('JWT_ALGORITHM','HS256')),
            $dec=JWT::decode($jwt, new Key($key, $alg));
            $request->attributes->add['user'=>$dec->'data'];
        }catch(exeption $e){
            response()->json(['error'=>'Expiredao'],401);
        }
        return $next($request);
    }
}
