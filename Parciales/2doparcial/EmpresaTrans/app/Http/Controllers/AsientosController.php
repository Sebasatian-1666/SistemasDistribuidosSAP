<?php

namespace App\Http\Controllers;

use App\Models\asientos;
use Illuminate\Http\Request;

class AsientosController extends Controller
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
        $datos=$request=>validate(['salida'=>'required|in:string',
        'numero'=>'required|in:integer',
        'CI'=>'required|in:string']);
    }

    /**
     * Display the specified resource.
     */
    public function show(asientos $asientos)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(asientos $asientos)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, asientos $asientos)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(asientos $asientos)
    {
        //
    }
}
