"use client"

import type React from "react"

import {useState} from "react"
import {Button} from "@/components/ui/button"
import {Input} from "@/components/ui/input"
import {Label} from "@/components/ui/label"
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card"
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table"
import {Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger} from "@/components/ui/dialog"
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select"
import {Trash2, Edit, Plus, Search} from "lucide-react"
import type {Tarifa, TipoTarifa} from "@/app/page"

interface TarifasCRUDProps {
    tarifas: Tarifa[]
    setTarifasAction: (tarifas: Tarifa[]) => void
    tiposTarifa: TipoTarifa[]
}

export function TarifasCRUD({tarifas, setTarifasAction, tiposTarifa}: TarifasCRUDProps) {
    const [searchTerm, setSearchTerm] = useState("")
    const [filtroTipo, setFiltroTipo] = useState("todos")
    const [isDialogOpen, setIsDialogOpen] = useState(false)
    const [editingTarifa, setEditingTarifa] = useState<Tarifa | null>(null)
    const [formData, setFormData] = useState({
        tipo_tarifa: "",
        monto: "",
        fechaVigencia: "",
    })

    const filteredTarifas = tarifas.filter((tarifa) => {
        const matchesSearch =
            tarifa.tipo_tarifa.toLowerCase().includes(searchTerm.toLowerCase()) ||
            tarifa.monto.toString().includes(searchTerm)

        const matchesTipo = filtroTipo === "todos" || tarifa.tipo_tarifa === filtroTipo

        return matchesSearch && matchesTipo
    })

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        const tarifaPayload = {
            tipo_tarifa: formData.tipo_tarifa,
            monto: Number(formData.monto),
            fechaVigencia: formData.fechaVigencia,
        }

        try {
            if (editingTarifa) {
                const response = await fetch(`http://localhost:8080/api/tarifas/${editingTarifa.id}`, {
                    method: "PUT",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(tarifaPayload),
                })
                if (!response.ok) throw new Error("Error actualizando tarifa")

                const updated = await response.json()
                setTarifasAction(tarifas.map((t) => (t.id === editingTarifa.id ? updated : t)))
            } else {
                const response = await fetch("http://localhost:8080/api/tarifas", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(tarifaPayload),
                })
                if (!response.ok) throw new Error("Error creando tarifa")

                const created = await response.json()
                setTarifasAction([...tarifas, created])
            }

            setFormData({tipo_tarifa: "", monto: "", fechaVigencia: ""})
            setEditingTarifa(null)
            setIsDialogOpen(false)
        } catch (error) {
            console.error("Error en handleSubmit:", error)
            alert("No se pudo guardar la tarifa 😢")
        }
    }


    const handleEdit = (tarifa: Tarifa) => {
        setEditingTarifa(tarifa)
        setFormData({
            tipo_tarifa: tarifa.tipo_tarifa,
            monto: tarifa.monto.toString(),
            fechaVigencia: tarifa.fechaVigencia,
        })
        setIsDialogOpen(true)
    }

    const handleDelete = async (id: string) => {
        try {
            const res = await fetch(`http://localhost:8080/api/tarifas/${id}`, {
                method: "DELETE",
            })
            if (!res.ok) throw new Error("Error al eliminar tarifa")

            setTarifasAction(tarifas.filter((t) => t.id !== id))
        } catch (error) {
            console.error("Error al eliminar tarifa:", error)
            alert("No se pudo eliminar la tarifa.")
        }
    }


    const openNewDialog = () => {
        setEditingTarifa(null)
        setFormData({
            tipo_tarifa: "",
            monto: "",
            fechaVigencia: "",
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
                            placeholder="Buscar por tipo de tarifa o monto..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="pl-10"
                        />
                    </div>
                    <Select value={filtroTipo} onValueChange={setFiltroTipo}>
                        <SelectTrigger className="w-[200px]">
                            <SelectValue placeholder="Filtrar por tipo"/>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="todos">Todos los tipos</SelectItem>
                            {tiposTarifa.map((tipo) => (
                                <SelectItem key={tipo.id} value={tipo.tipo_tarifa}>
                                    {tipo.tipo_tarifa}
                                </SelectItem>
                            ))}
                        </SelectContent>
                    </Select>
                    <Button
                        variant="outline"
                        onClick={() => {
                            setSearchTerm("")
                            setFiltroTipo("todos")
                        }}
                    >
                        Limpiar Filtros
                    </Button>
                    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
                        <DialogTrigger asChild>
                            <Button onClick={openNewDialog}>
                                <Plus className="h-4 w-4 mr-2"/>
                                Nueva Tarifa
                            </Button>
                        </DialogTrigger>
                        <DialogContent>
                            <DialogHeader>
                                <DialogTitle>{editingTarifa ? "Editar Tarifa" : "Nueva Tarifa"}</DialogTitle>
                            </DialogHeader>
                            <form onSubmit={handleSubmit} className="space-y-4">
                                <div>
                                    <Label htmlFor="tipo_tarifa">Tipo de Tarifa</Label>
                                    <Select
                                        value={formData.tipo_tarifa}
                                        onValueChange={(value) => setFormData({...formData, tipo_tarifa: value})}
                                    >
                                        <SelectTrigger>
                                            <SelectValue placeholder="Seleccionar tipo de tarifa"/>
                                        </SelectTrigger>
                                        <SelectContent>
                                            {tiposTarifa.map((tipo) => (
                                                <SelectItem key={tipo.id} value={tipo.tipo_tarifa}>
                                                    {tipo.tipo_tarifa}
                                                </SelectItem>
                                            ))}
                                        </SelectContent>
                                    </Select>
                                </div>
                                <div>
                                    <Label htmlFor="monto">Monto</Label>
                                    <Input
                                        id="monto"
                                        type="number"
                                        step="0.01"
                                        value={formData.monto}
                                        onChange={(e) => setFormData({...formData, monto: e.target.value})}
                                        required
                                    />
                                </div>
                                <div>
                                    <Label htmlFor="fechaVigencia">Fecha de Vigencia</Label>
                                    <Input
                                        id="fechaVigencia"
                                        type="date"
                                        value={formData.fechaVigencia}
                                        onChange={(e) => setFormData({...formData, fechaVigencia: e.target.value})}
                                        required
                                    />
                                </div>
                                <Button type="submit" className="w-full">
                                    {editingTarifa ? "Actualizar" : "Crear"} Tarifa
                                </Button>
                            </form>
                        </DialogContent>
                    </Dialog>
                </div>

                {/* Indicador de filtros activos */}
                {(searchTerm || filtroTipo !== "todos") && (
                    <div className="flex gap-2 text-sm text-muted-foreground">
                        <span>Filtros activos:</span>
                        {searchTerm && <span className="bg-secondary px-2 py-1 rounded">Búsqueda: "{searchTerm}"</span>}
                        {filtroTipo !== "todos" &&
                            <span className="bg-secondary px-2 py-1 rounded">Tipo: {filtroTipo}</span>}
                    </div>
                )}
            </div>

            {/* Tabla de tarifas */}
            <Card>
                <CardHeader>
                    <CardTitle>Lista de Tarifas ({filteredTarifas.length})</CardTitle>
                </CardHeader>
                <CardContent>
                    <Table>
                        <TableHeader>
                            <TableRow>
                                <TableHead>Tipo de Tarifa</TableHead>
                                <TableHead>Monto</TableHead>
                                <TableHead>Fecha de Vigencia</TableHead>
                                <TableHead>Acciones</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {filteredTarifas.map((tarifa) => (
                                <TableRow key={tarifa.id}>
                                    <TableCell>{tarifa.tipo_tarifa}</TableCell>
                                    <TableCell>${tarifa.monto.toFixed(2)}</TableCell>
                                    <TableCell>{new Date(tarifa.fechaVigencia).toLocaleDateString()}</TableCell>
                                    <TableCell>
                                        <div className="flex gap-2">
                                            <Button variant="outline" size="sm" onClick={() => handleEdit(tarifa)}>
                                                <Edit className="h-4 w-4"/>
                                            </Button>
                                            <Button variant="destructive" size="sm"
                                                    onClick={() => handleDelete(tarifa.id)}>
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
