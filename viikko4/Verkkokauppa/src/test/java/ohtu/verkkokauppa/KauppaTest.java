package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // m‰‰ritell‰‰n ett‰ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // m‰‰ritell‰‰n ett‰ tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehd‰‰n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, ett‰ pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
        // toistaiseksi ei v‰litetty kutsussa k‰ytetyist‰ parametreista
    }

    @Test
    public void ostetaanKaksiTuotetta() {
        Pankki pankkiMock = mock(Pankki.class);
        Viitegeneraattori viiteMock = mock(Viitegeneraattori.class);

        when(viiteMock.uusi()).thenReturn(42);

        Varasto varastoMock = mock(Varasto.class);

        when(varastoMock.saldo(1)).thenReturn(10);
        when(varastoMock.saldo(2)).thenReturn(10);

        when(varastoMock.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varastoMock.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 3));

        Kauppa k = new Kauppa(varastoMock, pankkiMock, viiteMock);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        verify(pankkiMock).tilisiirto("pekka", 42, "12345", "33333-44455", 8);
    }
    
    @Test
    public void poistetaanTuote(){
        Pankki pankkiMock = mock(Pankki.class);
        Viitegeneraattori viiteMock = mock(Viitegeneraattori.class);
        Varasto varastoMock = mock(Varasto.class);
        
        when(viiteMock.uusi()).thenReturn(42);
        
        Tuote t = new Tuote(1,"maito",5);

        when(varastoMock.saldo(1)).thenReturn(10);
        when(varastoMock.haeTuote(1)).thenReturn(t);
        
        Kauppa k = new Kauppa(varastoMock, pankkiMock, viiteMock);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.poistaKorista(1);
        
        verify(varastoMock, times(1)).palautaVarastoon(t);
        
//        k.tilimaksu("pekka", "12345");
        
//        verify(pankkiMock).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }

    @Test
    public void ostetaanKaksiSamaaTuotetta() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // m‰‰ritell‰‰n ett‰ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // m‰‰ritell‰‰n ett‰ tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehd‰‰n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, ett‰ pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 10);
        // toistaiseksi ei v‰litetty kutsussa k‰ytetyist‰ parametreista
    }

    @Test
    public void ostetaanYksiValidiJaYksiLoppunutTuote() {
        Pankki pankkiMock = mock(Pankki.class);
        Viitegeneraattori viiteMock = mock(Viitegeneraattori.class);

        when(viiteMock.uusi()).thenReturn(42);

        Varasto varastoMock = mock(Varasto.class);

        when(varastoMock.saldo(1)).thenReturn(10);
        when(varastoMock.saldo(2)).thenReturn(0);

        when(varastoMock.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varastoMock.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 3));

        Kauppa k = new Kauppa(varastoMock, pankkiMock, viiteMock);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        verify(pankkiMock).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }

    @Test
    public void aloitaAsiointiNollaa() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // m‰‰ritell‰‰n ett‰ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // m‰‰ritell‰‰n ett‰ tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehd‰‰n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "ei maito", 4));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, ett‰ pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 4);
        // toistaiseksi ei v‰litetty kutsussa k‰ytetyist‰ parametreista
    }

    @Test
    public void pyydetaanUusiViiteJokaiseenMaksuun() {
        Pankki pankkiMock = mock(Pankki.class);
        Viitegeneraattori viiteMock = mock(Viitegeneraattori.class);
        Varasto varastoMock = mock(Varasto.class);

        when(varastoMock.saldo(1)).thenReturn(10);
        when(varastoMock.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        Kauppa k = new Kauppa(varastoMock, pankkiMock, viiteMock);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(viiteMock, times(1)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(viiteMock, times(2)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(viiteMock, times(3)).uusi();
    }

    /*
    @Test
    public void pyydetaanUusiViiteJokaiseenMaksuun() {
        Pankki mockPankki = mock(Pankki.class);
        Viitegeneraattori mockViite = mock(Viitegeneraattori.class);

        kauppa = new Kauppa(mockPankki, mockViite);

        kauppa.aloitaOstokset();
        kauppa.lisaaOstos(5);
        kauppa.maksa("1111");

        // tarkistetaan ett√§ t√§ss√§ vaiheessa viitegeneraattorin metodia seuraava()
        // on kutsuttu kerran
        verify(mockViite, times(1)).seruaava();

        kauppa.aloitaOstokset();
        kauppa.lisaaOstos(1);
        kauppa.maksa("1234");

        // tarkistetaan ett√§ t√§ss√§ vaiheessa viitegeneraattorin metodia seuraava()
        // on kutsuttu kaksi kertaa
        verify(mockViite, times(2)).seruaava();

        kauppa.aloitaOstokset();
        kauppa.lisaaOstos(3);
        kauppa.maksa("4444");

        // tarkistetaan ett√§ t√§ss√§ vaiheessa viitegeneraattorin metodia seuraava()
        // on kutsuttu kolme kertaa        
        verify(mockViite, times(3)).seruaava();
    }
     */
}
