package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.sqspedido.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.sqspedido.dto.PedidoEventDTO;

import java.util.stream.Collectors;

public class PedidoEventMapper {

    public static PedidoBO toBO(PedidoEventDTO dto) {
        PedidoBO bo = new PedidoBO();
        bo.setCep(dto.getZipCode());

        PessoaBO pessoa = new PessoaBO();
        pessoa.setId(dto.getCustomerId());
        bo.setPessoa(pessoa);

        bo.setItens(dto.getOrderItems().stream().map(item -> {
            PedidoProdutoBO itemBO = new PedidoProdutoBO();
            ProdutoBO produto = new ProdutoBO();
            produto.setId(item.getSku());
            itemBO.setProduto(produto);
            itemBO.setQuantidade(item.getAmount());
            return itemBO;
        }).collect(Collectors.toList()));

        return bo;
    }
}