package br.edu.cio.util;

import br.edu.cio.model.dao.*;
import br.edu.cio.model.dao.mysql.*;

public class DAOFactory {
    public static UsuarioDAO gerarUsuarioDAO(){
        return new UsuarioDAOMySQL();
    }
    
    public static FuncionarioDAO gerarFuncionarioDAO(){
        return new FuncionarioDAOMySQL();
    }

    public static PropriedadeDAO gerarPropriedadeDAO(){
        return new PropriedadeDAOMySQL();
    }
    
    public static AreaPastagemDAO gerarAreaPastagemDAO(){
        return new AreaPastagemDAOMySQL();
    }
    
    public static LoteDAO gerarLoteDAO(){
        return new LoteDAOMySQL();
    }
    
    public static AnimalDAO gerarAnimalDAO(){
        return new AnimalDAOMySQL();
    }
    
    public static SensorDAO gerarSensorDAO(){
        return new SensorDAOMySQL();
    }
    
    public static UndProcessamentoDAO gerarUndProcessamentoDAO(){
        return new UndProcessamentoDAOMySQL();
    }
    
    public static DestinatarioDAO gerarDestinatarioDAO(){
        return new DestinatarioDAOMySQL();
    }
    
    public static ConfiguracaoDAO gerarConfiguracaoDAO(){
        return new ConfiguracaoDAOMySQL();
    }
    
    public static LeituraDAO gerarLeituraDAO(){
        return new LeituraDAOMySQL();
    }
    
    public static AnimalInfoDAO gerarAnimalInfoDAO(){
        return new AnimalInfoDAOMySQL();
    }
    
    public static MetodosInferenciaCioDAO gerarMetodosInferenciaCioDAO(){
        return new MetodosInferenciaCioDAOMySQL();
    }
    
    public static CioDAO gerarCioDAO(){
        return new CioDAOMySQL();
    }
    
    public static AlertaDAO gerarAlertaDAO(){
        return new AlertaDAOMySQL();
    }
    
    public static AlertaEmitidoDAO gerarAlertaEmitidoDAO(){
        return new AlertaEmitidoDAOMySQL();
    }
}
