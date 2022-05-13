<?php
	require 'SQLGlobal.php';

	if($_SERVER['REQUEST_METHOD']=='POST'){
		try{
			$datos = json_decode(file_get_contents("php://input"),true);

			$id = $datos["id"]; // obtener parametros GET
			$nombre = $datos["nombre"];
            $apellido = $datos["apellido"];
			$telefono = $datos["telefono"];
			$direccion = $datos["direccion"];
			$respuesta = SQLGlobal::cudFiltro(
				"insert into clientes(id,nombre,apellido,telefono,direccion) values(?,?,?,?,?)",
				array($id,$nombre,$apellido,$telefono,$direccion)
			);//con filtro ("El tamaño del array debe ser igual a la cantidad de los '?'")
			if($respuesta>0){
				echo json_encode(array(
					'respuesta'=>'200',
					'estado' => 'Se inserto correctamente el producto',
					'data'=>'El numero de registros afectados es: '.$respuesta,
					'error'=>''
				));
			} else {

				echo json_encode(array(
					'respuesta'=>'200',
					'estado' => 'No se inserto correctamente el producto',
					'data'=>'El numero de registros afectado es: '.$respuesta,
					'error'=>''
				));

			}
			
		}catch(PDOException $e){
			echo json_encode(
				array(
					'respuesta'=>'-1',
					'estado' => 'Ocurrio un error, intentelo mas tarde',
					'data'=>'',
					'error'=>$e->getMessage())
			);
		}
	}

?>