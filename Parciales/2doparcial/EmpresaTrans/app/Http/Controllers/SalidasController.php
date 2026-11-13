<?php

namespace App\Http\Controllers;

use App\Models\salidas;
use Illuminate\Http\Request;

class SalidasController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $datos=$request=>validate(['destino'=>'required|in:string',
        'fecha'=>'required|in:date',
        'hora'=>'required|in:dateTime',
        'bus'=>'required|in:string|unique',
        'precio'=>'required|in:decimal',
        'capacidad'=>'required|in:decimal']);
    }

    /**
     * Display the specified resource.
     */
    public function show(salidas $salidas)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(salidas $salidas)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, salidas $salidas)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(salidas $salidas)
    {
        //
    }
}
