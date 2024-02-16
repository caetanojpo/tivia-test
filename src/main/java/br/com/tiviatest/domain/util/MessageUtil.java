package br.com.tiviatest.domain.util;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.model.User;

public abstract class MessageUtil {
    private MessageUtil() {
    }

    public static final String MAP_ENT = "Mapeando Entidade";

    public static final String RETORNO_HTTP = "Retornando resposta da requisicao";

    public static final String INSERINDO_OBJETO_BD = "Inserindo {} na base de dados";
    public static final String BUSCANDO_OBJETO_BD = "Buscando {} na base de dados";
    public static final String ATUALIZANDO_OBJETO_BD = "Atualizando {} na base de dados";
    public static final String REMOVENDO_OBJETO_BD = "Removendo {} na base de dados";

    public static final String INICIANDO_BUSCA_POR_ID = "Iniciando busca por id de um {} na base de dados";

    public static final String LOGANDO_USER_SISTEMA = "Autenticando o usuário no sistema";

    public static final String BENEFICIARIO_ENTIDADE_NOME = Beneficiario.class.getSimpleName();
    public static final String DOCUMENTO_ENTIDADE_NOME = Documento.class.getSimpleName();
    public static final String USER_ENTIDADE_NOME = User.class.getSimpleName();
}
