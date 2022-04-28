package com.algaworks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.desconto.CalculadoraDescontoPrimeiraFaixa;
import com.algaworks.desconto.CalculadoraDescontoSegundaFaixa;
import com.algaworks.desconto.CalculadoraDescontoTerceiraFaixa;
import com.algaworks.desconto.CalculadoraFaixaDesconto;
import com.algaworks.desconto.SemDesconto;

public class PedidoTest {
	
	private PedidoBuilder pedido;
	
	@Before
	public void setup() {
		pedido = new PedidoBuilder();
	}

	private void assertResumoDoPedido(double valorTotal, double desconto) {
		ResumoPedido resumoPedido = pedido.construir().resumo();
		
		//assertEquals(valorTotal, resumoPedido.getValorTotal(), 0.0001);
		//assertEquals(desconto, resumoPedido.getDesconto(), 0.0001);
		assertEquals(new ResumoPedido(valorTotal, desconto), resumoPedido);
	}
		
	@Test
	public void deveCalcularValorTotalEDescontoParaPedidoVazio() throws Exception {
		assertResumoDoPedido(0.0, 0.0);
	}
	
	@Test
	public void DeveCalcularResumoParaUmItemSemDesconto() throws Exception {
		//pedido.adicionarItem(new ItemPedido("Sabonete", 5.0, 5));
		pedido.comItem(5.0, 5);
		assertResumoDoPedido(25.0, 0.0);
	}
	
	@Test
	public void deveCalcularResumoParaDoisItensSemDesconto() throws Exception {
		//pedido.adicionarItem(new ItemPedido("Sabonete", 3.0, 3));
		//pedido.adicionarItem(new ItemPedido("Pasta dental", 7.0, 3));
		pedido.comItem(3.0, 3)
			.comItem(7.0, 3);
		
		assertResumoDoPedido(30.0, 0);
	}
	
	@Test
	public void deveAplicarDescontoNa1aFaixa() throws Exception {
		//pedido.adicionarItem(new ItemPedido("Creme", 20.0, 20));
		pedido.comItem(20.0, 20);
		
		assertResumoDoPedido(400.0, 16.0);
	}
	
	@Test
	public void deveAplicarDescontoNa2aFaixa() throws Exception {
		//pedido.adicionarItem(new ItemPedido("Shampoo", 15.0, 30));
		//pedido.adicionarItem(new ItemPedido("Oleo", 15.0, 30));
		
		pedido.comItem(15.0, 30)
			  .comItem(15.0, 30);
		
		assertResumoDoPedido(900.0, 54.0);
	}
	
	@Test
	public void deveAplicarDescontoNa3aFaixa() throws Exception {
		//pedido.adicionarItem(new ItemPedido("Shampoo", 15.0, 30));
		//pedido.adicionarItem(new ItemPedido("Oleo", 15.0, 30));
		//pedido.adicionarItem(new ItemPedido("Creme", 10.0, 30));
		
		pedido.comItem(15.0, 30)
			  .comItem(15.0, 30)
			  .comItem(10.0, 30);
		
		assertResumoDoPedido(1200.0, 96.0);
	}
	
	//tratando exceções opção 01 - mais utilizado
	@Test(expected = QuantidadeDeItensInvalidaException.class)
	public void naoAceitarPedidoscomItensComQuantidadeNegativas() throws Exception {
		pedido.comItem(0.00, -10);
	}

	
	// trantando exceções opção 02 - TRY CATCH
/*	@Test
	public void naoAceitarPedidoscomItensComQuantidadeNegativas() throws Exception {
		try {
			pedido.comItem(0.00, -10);
			fail("Deveria ter lançado a exception QuantidadeDeItensInvalidaExceptions");
		} catch (QuantidadeDeItensInvalidaException e) {
			String message = e.getMessage();
			assertEquals("Não pode se negativo", message);
		}
*/		
	}


