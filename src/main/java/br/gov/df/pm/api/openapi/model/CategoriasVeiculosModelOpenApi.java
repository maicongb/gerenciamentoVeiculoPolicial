package br.gov.df.pm.api.openapi.model;

import br.gov.df.pm.api.model.CategoriaVeiculoModel;
import io.swagger.annotations.ApiModel;

//CONFIGURA NA CLASSE SpringFoxConfig adicionar o alternateTypeRules
@ApiModel("CategoriasVeiculosModel")
public class CategoriasVeiculosModelOpenApi extends PagedModelOpenApi<CategoriaVeiculoModel> {

}
