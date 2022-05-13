<?php
	require 'SQLGlobal.php';

	if($_SERVER['REQUEST_METHOD']=='POST'){
		try{
			$datos = json_decode(file_get_contents("php://input"),true);

			$id = $datos["id"]; // obtener parametros GET
            $descripcion = $datos["descripcion"];
            $fecha = $datos["fecha"];
            $monto = $datos["monto"];
            $nombreCliente = $datos["nombreCliente"];
            $apellidoCliente = $datos["apellidoCliente"];

			$respuesta = SQLGlobal::cudFiltro(
				"update ventas SET descripcion = ?, fecha = ?, monto = ?, nombreCliente = ?, apellidoCliente = ? where id = ?",
				array($descripcion,$fecha,$monto,$nombreCliente,$apellidoCliente,$id)
			);//con filtro ("El tamaño del array debe ser igual a la cantidad de los '?'")
			
            if($respuesta>0){
                echo json_encode(array(
                    'respuesta'=>'200',
                    'estado' => 'Se actualizo correctamente el registro',
                    'data'=>'Numero de registros afectados: '.$respuesta,
                    'error'=>''
                ));
            } else{
                echo json_encode(array(
                    'respuesta'=>'100',
                    'estado' => 'No se actualizo correctamente el registro',
                    'data'=>'Numero de registros afectados: '.$respuesta,
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