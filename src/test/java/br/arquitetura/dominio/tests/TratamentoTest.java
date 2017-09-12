package br.arquitetura.dominio.tests;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import br.arquitetura.dominio.CategoriaTratamento;
import br.arquitetura.dominio.Tratamento;

/**
 * Classe de validação dos objetos de dóminio
 * @author Herivelton
 * O JUnit (junit.org) é um framework muito simples para facilitar 
 * a criação destes testes de unidade e em especial sua execução. 
 * Ele possui alguns métodos que tornam seu código de teste bem legível e fácil de fazer as asserções.
 */
public class TratamentoTest {

	private static Validator validator;
	
	@BeforeClass
	public static void setUp(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void tratamentoDescricaoIsNull(){
		Tratamento obj = new Tratamento();
		obj.setId(1);
		obj.setDescricao(null);
		obj.setCategoriaTratamento(new CategoriaTratamento());
		Set<ConstraintViolation<Tratamento>> constraintViolations =
	    validator.validate(obj);
		assertEquals(1, constraintViolations.size());
		assertEquals("Campo descrição não pode ser vazio.", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void tratamentoDescricaoLenght(){
		Tratamento obj = new Tratamento();
		obj.setId(1);
		obj.setDescricao("TESTE");
		obj.setCategoriaTratamento(new CategoriaTratamento());
		Set<ConstraintViolation<Tratamento>> constraintViolations =
	    validator.validate(obj);
		assertEquals(1, constraintViolations.size());
		assertEquals("Campo descrição deve ter o tamanho entre 10 e 50.", constraintViolations.iterator().next().getMessage());
	}
	
}
