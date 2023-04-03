package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.Sensor;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AreaPastagemDAO;
import br.edu.cio.model.dao.LoteDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AnimalDAOMySQL implements AnimalDAO {

    Connection conexao;

    public AnimalDAOMySQL() {//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    
    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
    AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
    SensorDAO acessoBDSensor = DAOFactory.gerarSensorDAO();
    
    @Override
    public void cadastrarDAO(Animal animal) {
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //SimpleDateFormat formataOLD = new SimpleDateFormat("yyyy/MM/dd");
        Animal aux = buscarUltimoRegistroDAO();
        int cod = Integer.parseInt(aux.getCodigo()) + 1;
        String codigoAnimal = null;
        if (cod > 99) {
            codigoAnimal = "0000" + String.valueOf(cod);
        } else if (cod > 9) {
            codigoAnimal = "00000" + String.valueOf(cod);
        } else {
            codigoAnimal = "000000" + String.valueOf(cod);
        }
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            java.sql.Date dataNascimento = null;
            java.util.Date agora = new java.util.Date();
            String StrDataCadastro = formatoDataUSA.format(agora);
            String StrHoraCadastro = formatoHoraBr.format(agora);
            java.sql.Date dataCadastro = new java.sql.Date(formatoDataUSA.parse(StrDataCadastro).getTime());
            java.sql.Time horaCadastro = new java.sql.Time(formatoHoraBr.parse(StrHoraCadastro).getTime());
            if(animal.getDt_nascimento() != null){
                /*String StrDataNasc = "";
                int i=0;
                while(i<animal.getDt_nascimento().length()){
                    if(animal.getDt_nascimento().charAt(i) == '-')
                        StrDataNasc += "/";
                    else
                        StrDataNasc +=animal.getDt_nascimento().charAt(i);    
                    i++;
                }*/
                //dataNascimento = java.sql.Date.valueOf(StrDataNasc);
                //dataNascimento = new java.sql.Date(formatoCompletoUSA.parse(StrDataNasc).getTime());
                dataNascimento = new java.sql.Date(formatoDataUSA.parse(animal.getDt_nascimento()).getTime());
            }
            String sql = "INSERT INTO animal (ani_codigo, ani_apelido, ani_rgn, ani_sexo,"
                + " ani_grau_sangue, ani_tipo, ani_raca, ani_pelagem, ani_dt_nascimento,"
                + " ani_dt_cadastro, ani_hr_cadastro, ani_tipo_geracao, ani_estado_atual, ani_peso_nascimento, "
                + " ani_peso_atual, ani_preco_compra, ani_imagem, ani_observacao, ani_monitorando, ani_propriedade_fk, ani_lote_fk, ani_pasto_fk,"
                + " ani_sensor_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ps.setString(1, codigoAnimal);
            ps.setString(2, animal.getApelido());
            ps.setInt(3, animal.getRgn());
            ps.setString(4, animal.getSexo());
            ps.setString(5, animal.getGrau_sangue());
            ps.setString(6, animal.getTipo());
            ps.setString(7, animal.getRaca());
            ps.setString(8, animal.getPelagem());
            ps.setDate(9, dataNascimento);
            ps.setDate(10, dataCadastro);
            ps.setTime(11, horaCadastro);
            ps.setString(12, animal.getTipo_geracao());
            ps.setString(13, animal.getEstado_atual());
            ps.setDouble(14, animal.getPeso_nascimento());
            ps.setDouble(15, animal.getPeso_atual());
            ps.setDouble(16, animal.getPreco_compra());
            ps.setString(17, animal.getImagem());
            ps.setString(18, animal.getObservacao());
            ps.setInt(19, animal.getMonitorando());
            ps.setInt(20, animal.getPropriedade().getId());
            ps.setInt(21, animal.getLote().getId());
            ps.setInt(22, animal.getPasto().getId());
            if(animal.getSensor() != null)
                ps.setInt(23, animal.getSensor().getId());
            else
                ps.setString(23, null);

            ps.executeUpdate();
            ps.close();
            conexao.close();
            
            } catch (SQLException | ParseException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE Animal NÃO REALIZADA.", ex);
       }
    }

    @Override
    public void editarDAO(Animal animal) {
        SimpleDateFormat formata = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            java.sql.Time horaCadastro = null;
            java.sql.Date dataCadastro = null;
            java.sql.Date dataNascimento = null;
            if(animal.getDt_cadastro() != null){
                String StrDataCadastro = "";
                String StrHoraCadastro = "";
                int i=0;
                while(i<animal.getDt_cadastro().length()){
                    if(i<=9){
                        if(animal.getDt_cadastro().charAt(i) == '-')
                            StrDataCadastro += "/";
                        else
                            StrDataCadastro +=animal.getDt_cadastro().charAt(i);
                    }    
                    if(i>10)
                        StrHoraCadastro +=animal.getDt_cadastro().charAt(i);
                    i++;
                }
                StrHoraCadastro+= ":00";
                dataCadastro = new java.sql.Date(formata.parse(StrDataCadastro).getTime());
                horaCadastro = new java.sql.Time(formataHora.parse(StrHoraCadastro).getTime()); 
            }
            if(animal.getDt_nascimento() != null){
                String StrDataNasc = "";
                int i=0;
                while(i<animal.getDt_nascimento().length()){
                    if(animal.getDt_nascimento().charAt(i) == '-')
                        StrDataNasc += "/";
                    else
                        StrDataNasc +=animal.getDt_nascimento().charAt(i);    
                    i++;
                }
                //dataNascimento = java.sql.Date.valueOf(StrDataNasc);
                dataNascimento = new java.sql.Date(formata.parse(StrDataNasc).getTime());
            }
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql1 = "UPDATE animal SET ani_apelido=?,ani_rgn=?,ani_sexo=?,"
                    + "ani_grau_sangue=?,ani_tipo=?,ani_raca=?,"
                    + "ani_pelagem=?,ani_dt_nascimento=?,"
                    + "ani_dt_cadastro=?,ani_hr_cadastro=?, ani_tipo_geracao=?,"
                    + "ani_estado_atual=?,ani_peso_nascimento=?,"
                    + "ani_peso_atual=?,"
                    + "ani_preco_compra=?,ani_imagem=?,"
                    + "ani_observacao=?,ani_monitorando=?,"
                    + "ani_propriedade_fk=?,ani_lote_fk=?,"
                    + "ani_pasto_fk=?,ani_sensor_fk=? WHERE ani_id = ?";

            PreparedStatement ps2 = conexao.prepareStatement(sql1);

            ps2.setString(1, animal.getApelido());
            ps2.setInt(2, animal.getRgn());
            ps2.setString(3, animal.getSexo());
            ps2.setString(4, animal.getGrau_sangue());
            ps2.setString(5, animal.getTipo());
            ps2.setString(6, animal.getRaca());
            ps2.setString(7, animal.getPelagem());
            ps2.setDate(8, dataNascimento);
            ps2.setDate(9, dataCadastro);
            ps2.setTime(10, horaCadastro);
            ps2.setString(11, animal.getTipo_geracao());
            ps2.setString(12, animal.getEstado_atual());
            ps2.setDouble(13, animal.getPeso_nascimento());
            ps2.setDouble(14, animal.getPeso_atual());
            ps2.setDouble(15, animal.getPreco_compra());
            ps2.setString(16, animal.getImagem());
            ps2.setString(17, animal.getObservacao());
            ps2.setInt(18, animal.getMonitorando());
            ps2.setInt(19, animal.getPropriedade().getId());
            ps2.setInt(20, animal.getLote().getId());
            ps2.setInt(21, animal.getPasto().getId());
            if(animal.getSensor() != null)
                ps2.setInt(22, animal.getSensor().getId());
            else
                ps2.setString(22, null);
            ps2.setInt(23, animal.getId());

            ps2.executeUpdate();
            ps2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE Animal NÃO REALIZADA.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Animal buscarPorIdDAO(int id) {
        SimpleDateFormat formatoBrasil = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Animal a = new Animal();
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "SELECT * FROM animal WHERE ani_id = ? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                a = null;
            } else {
                a.setId(rs.getInt("ani_id"));
                a.setCodigo(rs.getNString("ani_codigo"));
                a.setApelido(rs.getString("ani_apelido"));
                a.setRgn(rs.getInt("ani_rgn"));
                a.setSexo(rs.getString("ani_sexo"));
                a.setGrau_sangue(rs.getString("ani_grau_sangue"));
                a.setTipo(rs.getString("ani_tipo"));
                a.setRaca(rs.getString("ani_raca"));
                a.setPelagem(rs.getString("ani_pelagem"));
                if(rs.getDate("ani_dt_nascimento") == null)
                        a.setDt_nascimento(null);
                    else
                        a.setDt_nascimento(formatoBD.format(rs.getDate("ani_dt_nascimento")));
                if(rs.getDate("ani_dt_cadastro") == null)
                    a.setDt_cadastro(null);
                else
                    a.setDt_cadastro(formatoBD.format(rs.getDate("ani_dt_cadastro")));
                if(rs.getDate("ani_hr_cadastro") == null)
                    a.setHr_cadastro(null);
                else
                    a.setHr_cadastro(formatoHora.format(rs.getTime("ani_hr_cadastro")));
                a.setTipo_geracao(rs.getString("ani_tipo_geracao"));
                a.setEstado_atual(rs.getString("ani_estado_atual"));
                a.setPeso_nascimento(rs.getDouble("ani_peso_nascimento"));
                a.setPeso_atual(rs.getDouble("ani_peso_atual"));
                a.setPreco_compra(rs.getDouble("ani_preco_compra"));
                a.setImagem(rs.getString("ani_imagem"));
                a.setObservacao(rs.getString("ani_observacao"));
                a.setMonitorando(Integer.parseInt(rs.getString("ani_monitorando")));
              
                PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                Propriedade p = acessoBDPropriedade.buscarPorIdDAO(rs.getInt("ani_propriedade_fk"));
                a.setPropriedade(p);

                LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
                Lote l = acessoBDLote.buscarPorIdDAO(rs.getInt("ani_lote_fk"));
                a.setLote(l);
                
                AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
                AreaPastagem area = acessoBDPasto.buscarPorIdDAO(rs.getInt("ani_pasto_fk")); 
                a.setPasto(area);
                
                SensorDAO acessoBDsensor = DAOFactory.gerarSensorDAO();
                Sensor s = acessoBDsensor.buscarPorIdDAO(rs.getInt("ani_sensor_fk"));
                a.setSensor(s);

            }
            ps.close();
            rs.close();
            conexao.close();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ANIMAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Animal> buscarDAO(String valorBusca) {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Animal> vaca = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {
                sql = "SELECT * FROM animal ";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM animal where ani_codigo like ? ";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                vaca = null;
            } else {
                do {
                    Animal a = new Animal();
                    a.setId(rs.getInt("ani_id"));
                    a.setCodigo(rs.getNString("ani_codigo"));
                    a.setApelido(rs.getString("ani_apelido"));
                    a.setRgn(rs.getInt("ani_rgn"));
                    a.setSexo(rs.getString("ani_sexo"));
                    a.setGrau_sangue(rs.getString("ani_grau_sangue"));
                    a.setTipo(rs.getString("ani_tipo"));
                    a.setRaca(rs.getString("ani_raca"));
                    a.setPelagem(rs.getString("ani_pelagem"));
                    if(rs.getDate("ani_dt_nascimento") == null)
                        a.setDt_nascimento(null);
                    else
                        a.setDt_nascimento(formata.format(rs.getDate("ani_dt_nascimento")));/* do formato BD para String Brasil*/
                    if(rs.getDate("ani_dt_cadastro") == null)
                        a.setDt_cadastro(null);
                    else
                        a.setDt_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                    if(rs.getTime("ani_hr_cadastro") == null)
                        a.setHr_cadastro(null);
                    else
                        a.setHr_cadastro(formata.format(rs.getTime("ani_hr_cadastro")));
                    a.setTipo_geracao(rs.getString("ani_tipo_geracao"));
                    a.setEstado_atual(rs.getString("ani_estado_atual"));
                    a.setPeso_nascimento(rs.getDouble("ani_peso_nascimento"));
                    a.setPeso_atual(rs.getDouble("ani_peso_atual"));
                    a.setPreco_compra(rs.getDouble("ani_preco_compra"));
                    a.setImagem(rs.getString("ani_imagem"));
                    a.setObservacao(rs.getString("ani_observacao"));
                    a.setMonitorando(Integer.parseInt(rs.getString("ani_monitorando")));
                    
                    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                    a.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("ani_propriedade_fk")));

                    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
                    a.setLote(acessoBDLote.buscarPorIdDAO(rs.getInt("ani_lote_fk")));

                    AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
                    a.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("ani_pasto_fk")));
                    
                    if(rs.getInt("ani_sensor_fk") != 0 ){
                        SensorDAO acessoBDsensor = DAOFactory.gerarSensorDAO();
                        a.setSensor(acessoBDsensor.buscarPorIdDAO(rs.getInt("ani_sensor_fk")));
                    } else                   
                        a.setSensor(null);
                    
                    vaca.add(a);
                
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return vaca;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ANIMAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Animal> listarPorPropriedadeDAO(int idPropriedade) {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Animal> animal = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM animal where ani_lote_fk = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPropriedade);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                animal = null;
            } else {
                do {
                    Animal a = new Animal();
                    a.setId(rs.getInt("ani_id"));
                    a.setCodigo(rs.getNString("ani_codigo"));
                    a.setApelido(rs.getString("ani_apelido"));
                    a.setRgn(rs.getInt("ani_rgn"));
                    a.setSexo(rs.getString("ani_sexo"));
                    a.setGrau_sangue(rs.getString("ani_grau_sangue"));
                    a.setTipo(rs.getString("ani_tipo"));
                    a.setRaca(rs.getString("ani_raca"));
                    a.setPelagem(rs.getString("ani_pelagem"));
                    if(rs.getDate("ani_dt_nascimento") == null)
                        a.setDt_nascimento(null);
                    else
                        a.setDt_nascimento(formata.format(rs.getDate("ani_dt_nascimento")));/* do formato BD para String Brasil*/
                    if(rs.getDate("ani_dt_cadastro") == null)
                        a.setDt_cadastro(null);
                    else
                        a.setDt_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                    if(rs.getTime("ani_hr_cadastro") == null)
                        a.setHr_cadastro(null);
                    else
                        a.setHr_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                    a.setTipo_geracao(rs.getString("ani_tipo_geracao"));
                    a.setEstado_atual(rs.getString("ani_estado_atual"));
                    a.setPeso_nascimento(rs.getDouble("ani_peso_nascimento"));
                    a.setPeso_atual(rs.getDouble("ani_peso_atual"));
                    a.setPreco_compra(rs.getDouble("ani_preco_compra"));
                    a.setImagem(rs.getString("ani_imagem"));
                    a.setObservacao(rs.getString("ani_observacao"));
                    a.setMonitorando(Integer.parseInt(rs.getString("ani_monitorando")));
                    
                    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                    a.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("ani_propriedade_fk")));

                    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
                    a.setLote(acessoBDLote.buscarPorIdDAO(rs.getInt("ani_lote_fk")));

                    AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
                    a.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("ani_pasto_fk")));
                    
                    if(rs.getInt("ani_sensor_fk") != 0 ){
                        SensorDAO acessoBDsensor = DAOFactory.gerarSensorDAO();
                        a.setSensor(acessoBDsensor.buscarPorIdDAO(rs.getInt("ani_sensor_fk")));
                    } else                   
                        a.setSensor(null);

                    animal.add(a);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return animal;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ANIMAL NÃO REALIZADA.", ex);
        }

    }

    @Override
    public Animal buscarUltimoRegistroDAO() {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            Animal a = new Animal();
            String sql = "SELECT * FROM animal ORDER BY ani_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                a = null;
            } else {
                a.setId(rs.getInt("ani_id"));
                a.setCodigo(rs.getNString("ani_codigo"));
                a.setApelido(rs.getString("ani_apelido"));
                a.setRgn(rs.getInt("ani_rgn"));
                a.setSexo(rs.getString("ani_sexo"));
                a.setGrau_sangue(rs.getString("ani_grau_sangue"));
                a.setTipo(rs.getString("ani_tipo"));
                a.setRaca(rs.getString("ani_raca"));
                a.setPelagem(rs.getString("ani_pelagem"));
                if(rs.getDate("ani_dt_nascimento") == null)
                    a.setDt_nascimento(null);
                else
                    a.setDt_nascimento(formata.format(rs.getDate("ani_dt_nascimento")));/* do formato BD para String Brasil*/
                if(rs.getDate("ani_dt_cadastro") == null)
                    a.setDt_cadastro(null);
                else
                    a.setDt_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                if(rs.getTime("ani_hr_cadastro") == null)
                    a.setHr_cadastro(null);
                else
                    a.setHr_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                a.setTipo_geracao(rs.getString("ani_tipo_geracao"));
                a.setEstado_atual(rs.getString("ani_estado_atual"));
                a.setPeso_nascimento(rs.getDouble("ani_peso_nascimento"));
                a.setPeso_atual(rs.getDouble("ani_peso_atual"));
                a.setPreco_compra(rs.getDouble("ani_preco_compra"));
                a.setImagem(rs.getString("ani_imagem"));
                a.setObservacao(rs.getString("ani_observacao"));
                a.setMonitorando(Integer.parseInt(rs.getString("ani_monitorando")));

                PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                a.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("ani_propriedade_fk")));

                LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
                a.setLote(acessoBDLote.buscarPorIdDAO(rs.getInt("ani_lote_fk")));

                AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
                a.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("ani_pasto_fk")));

                if(rs.getInt("ani_sensor_fk") != 0 ){
                    SensorDAO acessoBDsensor = DAOFactory.gerarSensorDAO();
                    a.setSensor(acessoBDsensor.buscarPorIdDAO(rs.getInt("ani_sensor_fk")));
                } else                   
                    a.setSensor(null);
            }
            ps.close();
            rs.close();
            conexao.close();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ANIMAL NÃO REALIZADA.", ex);
        }

    }

    @Override
    public boolean excluirDAO(int id) {
        boolean excluido = false;
        conexao = Conexao.getConnection();//start na conexão com banco de dados
        try {
            String sql = "DELETE FROM info_animal where info_animal_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            sql = "DELETE FROM previsao_cio where prevc_animal_fk=?";
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            sql = "DELETE FROM animal where ani_id=?";
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE ANIMAL NÃO REALIZADA.", ex);
        }
        return excluido;

    }
    
    @Override
    public Animal buscarPorIdSensorDAO(int idSensor) {
        try {
            conexao = Conexao.getConnection();
            Animal a = new Animal();
            String sql = "SELECT * FROM animal WHERE ani_sensor_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idSensor);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                a = null;
            } else {              
                SimpleDateFormat formata = new SimpleDateFormat("dd/MM/YYYY");
                a.setId(rs.getInt("ani_id"));
                a.setCodigo(rs.getNString("ani_codigo"));
                a.setApelido(rs.getString("ani_apelido"));
                a.setRgn(rs.getInt("ani_rgn"));
                a.setSexo(rs.getString("ani_sexo"));
                a.setGrau_sangue(rs.getString("ani_grau_sangue"));
                a.setTipo(rs.getString("ani_tipo"));
                a.setRaca(rs.getString("ani_raca"));
                a.setPelagem(rs.getString("ani_pelagem"));
                if(rs.getDate("ani_dt_nascimento") == null)
                    a.setDt_nascimento(null);
                else
                    a.setDt_nascimento(formata.format(rs.getDate("ani_dt_nascimento")));/* do formato BD para String Brasil*/
                if(rs.getDate("ani_dt_cadastro") == null)
                    a.setDt_cadastro(null);
                else
                    a.setDt_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                if(rs.getTime("ani_hr_cadastro") == null)
                    a.setHr_cadastro(null);
                else
                    a.setHr_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                a.setTipo_geracao(rs.getString("ani_tipo_geracao"));
                a.setEstado_atual(rs.getString("ani_estado_atual"));
                a.setPeso_nascimento(rs.getDouble("ani_peso_nascimento"));
                a.setPeso_atual(rs.getDouble("ani_peso_atual"));
                a.setPreco_compra(rs.getDouble("ani_preco_compra"));
                a.setImagem(rs.getString("ani_imagem"));
                a.setObservacao(rs.getString("ani_observacao"));
                a.setMonitorando(Integer.parseInt(rs.getString("ani_monitorando")));

                try {
                    a.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("ani_propriedade_fk")));
                } catch (SQLException ex) {
                    Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar a Propriedade a qual o Animal pertence.", ex);
                }
                try {
                    a.setLote(acessoBDLote.buscarPorIdDAO(rs.getInt("ani_lote_fk")));
                } catch (SQLException ex) {
                    Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar o Lote ao qual o Animal pertence.", ex);
                }
                try {
                    a.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("ani_pasto_fk")));
                } catch (SQLException ex) {
                    Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar o Pasto ao qual o Animal pertence.", ex);
                }
                try {
                    a.setSensor(acessoBDSensor.buscarPorIdDAO(rs.getInt("ani_sensor_fk")));
                } catch (SQLException ex) {
                    Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar o Sensor ao qual o Animal está vinculado.", ex);
                }
            }
            ps.close();
            rs.close();
            conexao.close();
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ANIMAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Animal> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Animal> carregarDAO() {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Animal> vaca = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            sql = "SELECT * FROM animal where ani_monitorando=? ";
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                vaca = null;
            } else {
                do {
                    Animal a = new Animal();
                    a.setId(rs.getInt("ani_id"));
                    a.setCodigo(rs.getNString("ani_codigo"));
                    a.setApelido(rs.getString("ani_apelido"));
                    a.setRgn(rs.getInt("ani_rgn"));
                    a.setSexo(rs.getString("ani_sexo"));
                    a.setGrau_sangue(rs.getString("ani_grau_sangue"));
                    a.setTipo(rs.getString("ani_tipo"));
                    a.setRaca(rs.getString("ani_raca"));
                    a.setPelagem(rs.getString("ani_pelagem"));
                    if(rs.getDate("ani_dt_nascimento") == null)
                        a.setDt_nascimento(null);
                    else
                        a.setDt_nascimento(formata.format(rs.getDate("ani_dt_nascimento")));/* do formato BD para String Brasil*/
                    if(rs.getDate("ani_dt_cadastro") == null)
                        a.setDt_cadastro(null);
                    else
                        a.setDt_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                    if(rs.getTime("ani_hr_cadastro") == null)
                        a.setHr_cadastro(null);
                    else
                        a.setHr_cadastro(formata.format(rs.getDate("ani_dt_cadastro")));
                    a.setTipo_geracao(rs.getString("ani_tipo_geracao"));
                    a.setEstado_atual(rs.getString("ani_estado_atual"));
                    a.setPeso_nascimento(rs.getDouble("ani_peso_nascimento"));
                    a.setPeso_atual(rs.getDouble("ani_peso_atual"));
                    a.setPreco_compra(rs.getDouble("ani_preco_compra"));
                    a.setImagem(rs.getString("ani_imagem"));
                    a.setObservacao(rs.getString("ani_observacao"));
                    a.setMonitorando(Integer.parseInt(rs.getString("ani_monitorando")));
                    
                    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                    a.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("ani_propriedade_fk")));

                    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
                    a.setLote(acessoBDLote.buscarPorIdDAO(rs.getInt("ani_lote_fk")));

                    AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
                    a.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("ani_pasto_fk")));
                    
                    if(rs.getInt("ani_sensor_fk") != 0 ){
                        SensorDAO acessoBDsensor = DAOFactory.gerarSensorDAO();
                        a.setSensor(acessoBDsensor.buscarPorIdDAO(rs.getInt("ani_sensor_fk")));
                    } else                   
                        a.setSensor(null);
                    vaca.add(a);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return vaca;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ANIMAL NÃO REALIZADA.", ex);
        }
    }

}
