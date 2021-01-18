package ba.unsa.etf.rpr.t7;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class KorisniciModel {
    private static KorisniciModel instanca=null;
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();
    private static Connection conn;
    private PreparedStatement stmt,izmjenaUpit,sviKorisniciUpit,dodajKorisnikaUpit,st;

    public KorisniciModel() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
            pripremiUpite();
            /*dodajKorisnikaUpit.setString(1, "Vedran");
            dodajKorisnikaUpit.setString(2,"Ljubović");
            dodajKorisnikaUpit.setString(3,"vljubovic@etf.unsa.ba");
            dodajKorisnikaUpit.setString(4,"vedranlj");
            dodajKorisnikaUpit.setString(5,"test");
            dodajKorisnikaUpit.setInt(6,1);
            dodajKorisnikaUpit.executeUpdate();
            dodajKorisnikaUpit.setString(1, "Amra");
            dodajKorisnikaUpit.setString(2,"Delić");
            dodajKorisnikaUpit.setString(3,"adelic@etf.unsa.ba");
            dodajKorisnikaUpit.setString(4,"amrad");
            dodajKorisnikaUpit.setString(5,"test");
            dodajKorisnikaUpit.setInt(6,2);
            dodajKorisnikaUpit.executeUpdate();
            dodajKorisnikaUpit.setString(1, "Tarik");
            dodajKorisnikaUpit.setString(2,"Sijerčić");
            dodajKorisnikaUpit.setString(3,"tsijercic1@etf.unsa.ba");
            dodajKorisnikaUpit.setString(4,"tariks");
            dodajKorisnikaUpit.setString(5,"test");
            dodajKorisnikaUpit.setInt(6,3);
            dodajKorisnikaUpit.executeUpdate();
            dodajKorisnikaUpit.setString(1, "Rijad");
            dodajKorisnikaUpit.setString(2,"Fejzić");
            dodajKorisnikaUpit.setString(3,"rfejzic1@etf.unsa.ba");
            dodajKorisnikaUpit.setString(4,"rijadf");
            dodajKorisnikaUpit.setString(5,"test");
            dodajKorisnikaUpit.setInt(6,4);
            dodajKorisnikaUpit.executeUpdate();*/


        } catch(SQLException e) {
            System.out.println("Neuspješno čitanje iz baze: " + e.getMessage());
        }

        if (trenutniKorisnik == null) trenutniKorisnik = new SimpleObjectProperty<>();

    }
    private void pripremiUpite() throws SQLException {
        dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnik VALUES(?, ?, ?, ?, ?, ?)");
        izmjenaUpit=conn.prepareStatement("UPDATE korisnik SET ime=?,prezime=?,email=?,username=?,password=? WHERE id=?");
        sviKorisniciUpit=conn.prepareStatement("SELECT * FROM korisnik");
    }
    public static KorisniciModel getInstance() {
        if (instanca == null) instanca=new KorisniciModel();
        return instanca;
    }

    public static void removeInstance() {
        if (instanca != null) {
            try {
                conn.close();
                instanca = null;
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }
        }
    }

    public void napuni() {
        // Ako je lista već bila napunjena, praznimo je
        // Na taj način se metoda napuni() može pozivati više puta u testovima
        korisnici.clear();
        try {
            ResultSet rs = sviKorisniciUpit.executeQuery();
            while (rs.next()) {
                Korisnik k = new Korisnik(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5));
                k.setId(rs.getInt(6));
                korisnici.add(k);
            }
        }
            catch(SQLException e){
                e.printStackTrace();
            }
        /*korisnici.add(new Korisnik("Vedran", "Ljubović", "vljubovic@etf.unsa.ba", "vedranlj", "test"));
        korisnici.add(new Korisnik("Amra", "Delić", "adelic@etf.unsa.ba", "amrad", "test"));
        korisnici.add(new Korisnik("Tarik", "Sijerčić", "tsijercic1@etf.unsa.ba", "tariks", "test"));
        korisnici.add(new Korisnik("Rijad", "Fejzić", "rfejzic1@etf.unsa.ba", "rijadf", "test"));*/
        trenutniKorisnik.set(null);
    }

    public void vratiNaDefault(){
        // Dodali smo metodu vratiNaDefault koja trenutno ne radi ništa, a kada prebacite Model na DAO onda
        // implementirajte ovu metodu
        // Razlog za ovo je da polazni testovi ne bi padali nakon što dodate bazu
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            stmt.executeUpdate("DELETE FROM korisnik");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        regenerisiBazu();
    }

    private void regenerisiBazu() {
        Scanner ulaz=null;
        try{
            ulaz=new Scanner(new FileInputStream("korisnici.db.sql"));
            String sqlUpit="";
            while(ulaz.hasNext()){
                sqlUpit+=ulaz.nextLine();
                if(sqlUpit.charAt(sqlUpit.length()-1)==';'){
                    try{
                        Statement stmt=conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit="";
                    }
                    catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void diskonektuj() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void izmijeni(Korisnik k){
        try{
            if(k!=null) {
                izmjenaUpit.setString(1, k.getIme());
                izmjenaUpit.setString(2, k.getPrezime());
                izmjenaUpit.setString(3, k.getEmail());
                izmjenaUpit.setString(4, k.getUsername());
                izmjenaUpit.setString(5, k.getPassword());
                izmjenaUpit.setInt(6, k.getId());
                izmjenaUpit.executeUpdate();
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setTrenutniKorisnik(int i) {
        this.trenutniKorisnik.set(korisnici.get(i));
    }

    public ObservableList<Korisnik> sviKorisnici() {
        ObservableList<Korisnik> rezultat=FXCollections.observableArrayList();
        try{
            ResultSet rs=sviKorisniciUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Korisnik(rs.getString(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }


        return rezultat;
    }
}
