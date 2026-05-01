package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.sqspedido.listener;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PedidoServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.sqspedido.dto.PedidoEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.sqspedido.mapper.PedidoEventMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PedidoSqsAdapter {

    private static final Logger log = LoggerFactory.getLogger(PedidoSqsAdapter.class);
    private final PedidoServicePort pedidoServicePort;

    public PedidoSqsAdapter(PedidoServicePort pedidoServicePort) {
        this.pedidoServicePort = pedidoServicePort;
    }

    @SqsListener(value = "${queue.order-events}")
    public void receberPedido(PedidoEventDTO evento) {
        try {
            log.info("Novo pedido recebido via SQS da origem: {}", evento.getOrigin());

            PedidoBO bo = PedidoEventMapper.toBO(evento);

            // Reutiliza a lógica de negócio já existente
            pedidoServicePort.criarPedido(bo);

            log.info("Pedido processado e persistido com sucesso para o cliente {}", evento.getCustomerId());
        } catch (Exception e) {
            log.error("Erro ao processar pedido vindo do SQS: {}", e.getMessage());
            throw e; // Lança para que o SQS possa gerenciar a retentativa (DLQ)
        }
    }
}