"use client"

import { useState, useEffect } from "react"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { CuentasCRUD } from "@/components/cuentas-crud"
import { TarifasCRUD } from "@/components/tarifas-crud"
import { TiposTarifaCRUD } from "@/components/tipos-tarifa-crud"

// Tipos de datos
export interface Cuenta {
  id: string
  entidad_bancaria: string
  numero_cuenta: number
  saldo: number
  id_titular: string
}

export interface Tarifa {
  id: string
  tipo_tarifa: string
  monto: number
  fechaVigencia: string
}

export interface TipoTarifa {
  id: string
  tipo_tarifa: string
}

export default function Component() {
  // Estados para cada entidad
    const [cuentas, setCuentasAction] = useState<Cuenta[]>([])
    const [tarifas, setTarifasAction] = useState<Tarifa[]>([])
    const [tiposTarifa, setTiposTarifaAction] = useState<TipoTarifa[]>([])

    // 👉 Cargar los datos al montar el componente
    useEffect(() => {
        fetch("http://localhost:8080/api/cuentas")
            .then(res => res.json())
            .then(data => setCuentasAction(data))

        fetch("http://localhost:8080/api/tarifas")
            .then(res => res.json())
            .then(data => setTarifasAction(data))

        fetch("http://localhost:8080/api/tipo_tarifas")
            .then(res => res.json())
            .then(data => setTiposTarifaAction(data))
    }, [])

  return (
    <div className="container mx-auto p-6">
      <div className="mb-8">
        <h1 className="text-3xl font-bold">Sistema de Gestión Bancaria</h1>
        <p className="text-muted-foreground">Administra cuentas, tarifas y tipos de tarifas</p>
      </div>

      <Tabs defaultValue="cuentas" className="w-full">
        <TabsList className="grid w-full grid-cols-3">
          <TabsTrigger value="cuentas">Cuentas</TabsTrigger>
          <TabsTrigger value="tarifas">Tarifas</TabsTrigger>
          <TabsTrigger value="tipos-tarifa">Tipos de Tarifa</TabsTrigger>
        </TabsList>

        <TabsContent value="cuentas">
          <Card>
            <CardHeader>
              <CardTitle>Gestión de Cuentas</CardTitle>
              <CardDescription>Administra las cuentas bancarias del sistema</CardDescription>
            </CardHeader>
            <CardContent>
              <CuentasCRUD cuentas={cuentas} setCuentasAction={setCuentasAction} />
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="tarifas">
          <Card>
            <CardHeader>
              <CardTitle>Gestión de Tarifas</CardTitle>
              <CardDescription>Administra las tarifas y sus montos</CardDescription>
            </CardHeader>
            <CardContent>
              <TarifasCRUD tarifas={tarifas} setTarifasAction={setTarifasAction} tiposTarifa={tiposTarifa} />
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="tipos-tarifa">
          <Card>
            <CardHeader>
              <CardTitle>Gestión de Tipos de Tarifa</CardTitle>
              <CardDescription>Administra los tipos de tarifa disponibles</CardDescription>
            </CardHeader>
            <CardContent>
              <TiposTarifaCRUD tiposTarifa={tiposTarifa} setTiposTarifaAction={setTiposTarifaAction} />
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}
