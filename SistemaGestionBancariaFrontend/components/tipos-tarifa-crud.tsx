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
import type {TipoTarifa} from "@/app/page"
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select"

interface TiposTarifaCRUDProps {
    tiposTarifa: TipoTarifa[]
    setTiposTarifaAction: (tiposTarifa: TipoTarifa[]) => void
}

export function TiposTarifaCRUD({tiposTarifa, setTiposTarifaAction}: TiposTarifaCRUDProps) {
    const [searchTerm, setSearchTerm] = useState("")
    const [filtroAvanzado, setFiltroAvanzado] = useState("all") // Updated default value
    const [isDialogOpen, setIsDialogOpen] = useState(false)
    const [editingTipo, setEditingTipo] = useState<TipoTarifa | null>(null)
    const [formData, setFormData] = useState({
        tipo_tarifa: "",
    })

    const filteredTipos = tiposTarifa.filter((tipo) => {
        const matchesSearch = tipo.tipo_tarifa.toLowerCase().includes(searchTerm.toLowerCase())

        let matchesAvanzado = true
        if (filtroAvanzado === "premium") {
            matchesAvanzado = tipo.tipo_tarifa.toLowerCase().includes("premium")
        } else if (filtroAvanzado === "basica") {
            matchesAvanzado =
                tipo.tipo_tarifa.toLowerCase().includes("basica") || tipo.tipo_tarifa.toLowerCase().includes("basic")
        } else if (filtroAvanzado === "especial") {
            matchesAvanzado =
                !tipo.tipo_tarifa.toLowerCase().includes("premium") &&
                !tipo.tipo_tarifa.toLowerCase().includes("basica") &&
                !tipo.tipo_tarifa.toLowerCase().includes("basic")
        }

        return matchesSearch && matchesAvanzado
    })

    // Obtener estadísticas de tipos
    const tiposStats = {
        total: tiposTarifa.length,
        premium: tiposTarifa.filter((t) => t.tipo_tarifa.toLowerCase().includes("premium")).length,
        basica: tiposTarifa.filter(
            (t) => t.tipo_tarifa.toLowerCase().includes("basica") || t.tipo_tarifa.toLowerCase().includes("basic"),
        ).length,
        especial: tiposTarifa.filter(
            (t) =>
                !t.tipo_tarifa.toLowerCase().includes("premium") &&
                !t.tipo_tarifa.toLowerCase().includes("basica") &&
                !t.tipo_tarifa.toLowerCase().includes("basic"),
        ).length,
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        const payload = {tipo_tarifa: formData.tipo_tarifa}

        try {
            if (editingTipo) {
                const res = await fetch(`http://localhost:8080/api/tipo_tarifas/${editingTipo.id}`, {
                    method: "PUT",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(payload),
                })
                if (!res.ok) throw new Error("Error actualizando tipo")

                const updated = await res.json()
                setTiposTarifaAction(tiposTarifa.map((tipo) => (tipo.id === editingTipo.id ? updated : tipo)))
            } else {
                const res = await fetch("http://localhost:8080/api/tipo_tarifas", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(payload),
                })
                if (!res.ok) throw new Error("Error creando tipo")

                const created = await res.json()
                setTiposTarifaAction([...tiposTarifa, created])
            }

            setFormData({tipo_tarifa: ""})
            setEditingTipo(null)
            setIsDialogOpen(false)
        } catch (err) {
            console.error("Error en handleSubmit:", err)
            alert("No se pudo guardar el tipo de tarifa.")
        }
    }


    const handleEdit = (tipo: TipoTarifa) => {
        setEditingTipo(tipo)
        setFormData({tipo_tarifa: tipo.tipo_tarifa})
        setIsDialogOpen(true)
    }

    const handleDelete = async (id: string) => {
        try {
            console.log("Intentando borrar tipo con ID:", id);
            const res = await fetch(`http://localhost:8080/api/tipo_tarifas/${id}`, {
                method: "DELETE",
            })
            if (!res.ok) {
                const msg = await res.text();
                throw new Error(`Error al eliminar tipo: ${msg}`)
            }

            setTiposTarifaAction(tiposTarifa.filter((tipo) => tipo.id !== id))
        } catch (err) {
            console.error("Error eliminando tipo:", err)
            alert("No se pudo eliminar el tipo de tarifa.")
        }
    }


    const openNewDialog = () => {
        setEditingTipo(null)
        setFormData({tipo_tarifa: ""})
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
                            placeholder="Buscar tipo de tarifa..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="pl-10"
                        />
                    </div>
                    <Select value={filtroAvanzado} onValueChange={setFiltroAvanzado}>
                        <SelectTrigger className="w-[200px]">
                            <SelectValue placeholder="Filtrar por categoría"/>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="all">Todas las categorías</SelectItem>
                            <SelectItem value="premium">Premium ({tiposStats.premium})</SelectItem>
                            <SelectItem value="basica">Básica ({tiposStats.basica})</SelectItem>
                            <SelectItem value="especial">Especiales ({tiposStats.especial})</SelectItem>
                        </SelectContent>
                    </Select>
                    <Button
                        variant="outline"
                        onClick={() => {
                            setSearchTerm("")
                            setFiltroAvanzado("all")
                        }}
                    >
                        Limpiar Filtros
                    </Button>
                    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
                        <DialogTrigger asChild>
                            <Button onClick={openNewDialog}>
                                <Plus className="h-4 w-4 mr-2"/>
                                Nuevo Tipo
                            </Button>
                        </DialogTrigger>
                        <DialogContent>
                            <DialogHeader>
                                <DialogTitle>{editingTipo ? "Editar Tipo de Tarifa" : "Nuevo Tipo de Tarifa"}</DialogTitle>
                            </DialogHeader>
                            <form onSubmit={handleSubmit} className="space-y-4">
                                <div>
                                    <Label htmlFor="tipo_tarifa">Tipo de Tarifa</Label>
                                    <Input
                                        id="tipo_tarifa"
                                        value={formData.tipo_tarifa}
                                        onChange={(e) => setFormData({tipo_tarifa: e.target.value})}
                                        placeholder="Ej: PREMIUM, BASICA, VIP..."
                                        required
                                    />
                                </div>
                                <Button type="submit" className="w-full">
                                    {editingTipo ? "Actualizar" : "Crear"} Tipo de Tarifa
                                </Button>
                            </form>
                        </DialogContent>
                    </Dialog>
                </div>

                {/* Indicador de filtros activos y estadísticas */}
                <div className="flex justify-between items-center">
                    <div className="flex gap-2 text-sm text-muted-foreground">
                        {(searchTerm || filtroAvanzado !== "all") && (
                            <>
                                <span>Filtros activos:</span>
                                {searchTerm &&
                                    <span className="bg-secondary px-2 py-1 rounded">Búsqueda: "{searchTerm}"</span>}
                                {filtroAvanzado !== "all" && (
                                    <span className="bg-secondary px-2 py-1 rounded">Categoría: {filtroAvanzado}</span>
                                )}
                            </>
                        )}
                    </div>
                    <div className="text-sm text-muted-foreground">Total: {tiposStats.total} tipos</div>
                </div>
            </div>

            {/* Tabla de tipos de tarifa */}
            <Card>
                <CardHeader>
                    <CardTitle>Lista de Tipos de Tarifa ({filteredTipos.length})</CardTitle>
                </CardHeader>
                <CardContent>
                    <Table>
                        <TableHeader>
                            <TableRow>
                                <TableHead>Tipo de Tarifa</TableHead>
                                <TableHead>Acciones</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {filteredTipos.map((tipo) => (
                                <TableRow key={tipo.id}>
                                    <TableCell className="font-medium">{tipo.tipo_tarifa}</TableCell>
                                    <TableCell>
                                        <div className="flex gap-2">
                                            <Button variant="outline" size="sm" onClick={() => handleEdit(tipo)}>
                                                <Edit className="h-4 w-4"/>
                                            </Button>
                                            <Button variant="destructive" size="sm"
                                                    onClick={() => handleDelete(tipo.id)}>
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
