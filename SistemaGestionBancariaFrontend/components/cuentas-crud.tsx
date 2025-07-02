"use client"

import type React from "react"

import {useState} from "react"
import {Button} from "@/components/ui/button"
import {Input} from "@/components/ui/input"
import {Label} from "@/components/ui/label"
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card"
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table"
import {Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger} from "@/components/ui/dialog"
import {Trash2, Edit, Plus, Search} from "lucide-react"
import type {Cuenta} from "@/app/page"
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select"

interface CuentasCRUDProps {
    cuentas: Cuenta[]
    setCuentasAction: (cuentas: Cuenta[]) => void
}

export function CuentasCRUD({cuentas, setCuentasAction}: CuentasCRUDProps) {
    const [searchTerm, setSearchTerm] = useState("")
    const [filtroEntidad, setFiltroEntidad] = useState("todas")
    const [isDialogOpen, setIsDialogOpen] = useState(false)
    const [editingCuenta, setEditingCuenta] = useState<Cuenta | null>(null)
    const [formData, setFormData] = useState({
        entidad_bancaria: "",
        numero_cuenta: "",
        saldo: "",
        id_titular: "",
    })

    const filteredCuentas = cuentas.filter((cuenta) => {
        const isNumeric = /^\d+$/.test(searchTerm)
        const matchesSearch =
            cuenta.entidad_bancaria.toLowerCase().includes(searchTerm.toLowerCase()) ||
            cuenta.numero_cuenta.toString().includes(searchTerm) ||
            cuenta.id_titular === (isNumeric ? parseInt(searchTerm, 10) : -1)

        const matchesEntidad =
            filtroEntidad === "todas" ||
            cuenta.entidad_bancaria === filtroEntidad

        return matchesSearch && matchesEntidad
    })


    // Obtener entidades únicas para el filtro
    const entidadesUnicas = Array.from(new Set(cuentas.map((cuenta) => cuenta.entidad_bancaria)))

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        const cuentaPayload = {
            entidad_bancaria: formData.entidad_bancaria,
            numero_cuenta: Number(formData.numero_cuenta),
            saldo: Number(formData.saldo),
            id_titular: Number(formData.id_titular),
        }

        try {
            if (editingCuenta) {
                // 🔄 PUT: Actualizar cuenta existente
                const response = await fetch(`http://localhost:8080/api/cuentas/${editingCuenta.id}`, {
                    method: "PUT",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(cuentaPayload),
                })
                if (!response.ok) throw new Error("Error actualizando cuenta")

                const updated = await response.json()
                setCuentasAction(
                    cuentas.map((cuenta) => (cuenta.id === editingCuenta.id ? updated : cuenta))
                )
            } else {
                // ➕ POST: Crear nueva cuenta
                const response = await fetch("http://localhost:8080/api/cuentas", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(cuentaPayload),
                })
                if (!response.ok) throw new Error("Error creando cuenta")

                const created = await response.json()
                setCuentasAction([...cuentas, created])
            }

            // Reset form y cerrar diálogo
            setFormData({entidad_bancaria: "", numero_cuenta: "", saldo: "", id_titular: ""})
            setEditingCuenta(null)
            setIsDialogOpen(false)
        } catch (error) {
            console.error("Error al guardar la cuenta:", error)
            alert("Ocurrió un error al guardar la cuenta 😢")
        }
    }


    const handleEdit = (cuenta: Cuenta) => {
        setEditingCuenta(cuenta)
        setFormData({
            entidad_bancaria: cuenta.entidad_bancaria,
            numero_cuenta: cuenta.numero_cuenta.toString(),
            saldo: cuenta.saldo.toString(),
            id_titular: cuenta.id_titular.toString(),
        })
        setIsDialogOpen(true)
    }

    const handleDelete = async (id: string) => {
        try {
            const res = await fetch(`http://localhost:8080/api/cuentas/${id}`, {
                method: "DELETE",
            })
            if (!res.ok) throw new Error("Error eliminando cuenta")

            setCuentasAction(cuentas.filter((cuenta) => cuenta.id !== id))
        } catch (error) {
            console.error("Error al eliminar cuenta:", error)
            alert("No se pudo eliminar la cuenta.")
        }
    }


    const openNewDialog = () => {
        setEditingCuenta(null)
        setFormData({
            entidad_bancaria: "",
            numero_cuenta: "",
            saldo: "",
            id_titular: "",
        })
        setIsDialogOpen(true)
    }

    return (
        <div className="space-y-4">
            {/* Controles de filtrado */}
            <div className="space-y-4">
                <div className="flex gap-4 items-center">
                    <div className="relative flex-1">
                        <Search
                            className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4"/>
                        <Input
                            placeholder="Buscar por entidad, número de cuenta o titular..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="pl-10"
                        />
                    </div>
                    <Select value={filtroEntidad} onValueChange={setFiltroEntidad}>
                        <SelectTrigger className="w-[200px]">
                            <SelectValue placeholder="Filtrar por entidad"/>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="todas">Todas las entidades</SelectItem>
                            {entidadesUnicas.map((entidad) => (
                                <SelectItem key={entidad} value={entidad}>
                                    {entidad}
                                </SelectItem>
                            ))}
                        </SelectContent>
                    </Select>
                    <Button
                        variant="outline"
                        onClick={() => {
                            setSearchTerm("")
                            setFiltroEntidad("todas")
                        }}
                    >
                        Limpiar Filtros
                    </Button>
                    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
                        <DialogTrigger asChild>
                            <Button onClick={openNewDialog}>
                                <Plus className="h-4 w-4 mr-2"/>
                                Nueva Cuenta
                            </Button>
                        </DialogTrigger>
                        <DialogContent>
                            <DialogHeader>
                                <DialogTitle>{editingCuenta ? "Editar Cuenta" : "Nueva Cuenta"}</DialogTitle>
                            </DialogHeader>
                            <form onSubmit={handleSubmit} className="space-y-4">
                                <div>
                                    <Label htmlFor="entidad_bancaria">Entidad Bancaria</Label>
                                    <Input
                                        id="entidad_bancaria"
                                        value={formData.entidad_bancaria}
                                        onChange={(e) => setFormData({...formData, entidad_bancaria: e.target.value})}
                                        required
                                    />
                                </div>
                                <div>
                                    <Label htmlFor="numero_cuenta">Número de Cuenta</Label>
                                    <Input
                                        id="numero_cuenta"
                                        type="number"
                                        value={formData.numero_cuenta}
                                        onChange={(e) => setFormData({...formData, numero_cuenta: e.target.value})}
                                        required
                                    />
                                </div>
                                <div>
                                    <Label htmlFor="saldo">Saldo</Label>
                                    <Input
                                        id="saldo"
                                        type="number"
                                        step="0.01"
                                        value={formData.saldo}
                                        onChange={(e) => setFormData({...formData, saldo: e.target.value})}
                                        required
                                    />
                                </div>
                                <div>
                                    <Label htmlFor="id_titular">ID Titular</Label>
                                    <Input
                                        id="id_titular"
                                        value={formData.id_titular}
                                        onChange={(e) => setFormData({...formData, id_titular: e.target.value})}
                                        required
                                    />
                                </div>
                                <Button type="submit" className="w-full">
                                    {editingCuenta ? "Actualizar" : "Crear"} Cuenta
                                </Button>
                            </form>
                        </DialogContent>
                    </Dialog>
                </div>

                {/* Indicador de filtros activos */}
                {(searchTerm || filtroEntidad !== "todas") && (
                    <div className="flex gap-2 text-sm text-muted-foreground">
                        <span>Filtros activos:</span>
                        {searchTerm && <span className="bg-secondary px-2 py-1 rounded">Búsqueda: "{searchTerm}"</span>}
                        {filtroEntidad !== "todas" && (
                            <span className="bg-secondary px-2 py-1 rounded">Entidad: {filtroEntidad}</span>
                        )}
                    </div>
                )}
            </div>

            {/* Tabla de cuentas */}
            <Card>
                <CardHeader>
                    <CardTitle>Lista de Cuentas ({filteredCuentas.length})</CardTitle>
                </CardHeader>
                <CardContent>
                    <Table>
                        <TableHeader>
                            <TableRow>
                                <TableHead>Entidad Bancaria</TableHead>
                                <TableHead>Número de Cuenta</TableHead>
                                <TableHead>Saldo</TableHead>
                                <TableHead>ID Titular</TableHead>
                                <TableHead>Acciones</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {filteredCuentas.map((cuenta) => (
                                <TableRow key={cuenta.id}>
                                    <TableCell>{cuenta.entidad_bancaria}</TableCell>
                                    <TableCell>{cuenta.numero_cuenta}</TableCell>
                                    <TableCell>${cuenta.saldo.toFixed(2)}</TableCell>
                                    <TableCell>{cuenta.id_titular}</TableCell>
                                    <TableCell>
                                        <div className="flex gap-2">
                                            <Button variant="outline" size="sm" onClick={() => handleEdit(cuenta)}>
                                                <Edit className="h-4 w-4"/>
                                            </Button>
                                            <Button variant="destructive" size="sm"
                                                    onClick={() => handleDelete(cuenta.id)}>
                                                <Trash2 className="h-4 w-4"/>
                                            </Button>
                                        </div>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </CardContent>
            </Card>
        </div>
    )
}