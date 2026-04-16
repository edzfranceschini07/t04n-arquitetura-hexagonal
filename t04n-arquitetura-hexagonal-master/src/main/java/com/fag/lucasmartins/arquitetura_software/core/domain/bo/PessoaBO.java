package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class PessoaBO {

    private UUID id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;

    public PessoaBO() {
        this.id = UUID.randomUUID();
    }

    public void validarMaioridade() {
        if (this.dataNascimento == null) {
            throw new DomainException("Data de nascimento é obrigatória.");
        }

        if (Period.between(this.dataNascimento, LocalDate.now()).getYears() < 18) {
            throw new DomainException("Pessoa deve ter no mínimo 18 anos.");
        }
    }

    public void validarCpf() {
        if (this.cpf == null || this.cpf.trim().isEmpty()) {
            throw new DomainException("CPF é obrigatório.");
        }

        String cpfLimpo = this.cpf.trim();
        if (cpfLimpo.length() != 11) {
            throw new DomainException("CPF deve possuir 11 caracteres.");
        }
    }

    public void validarTelefone() {
        if (this.telefone == null || this.telefone.trim().isEmpty()) {
            throw new DomainException("Telefone é obrigatório.");
        }

        String telefoneLimpo = this.telefone.trim();
        if (telefoneLimpo.length() != 11) {
            throw new DomainException("Telefone deve possuir 11 caracteres.");
        }
    }

    public void validarEmail() {
        if (this.email == null || this.email.trim().isEmpty()) {
            throw new DomainException("E-mail é obrigatório.");
        }

        String emailLimpo = this.email.trim();
        if (!emailLimpo.contains("@")) {
            throw new DomainException("E-mail inválido.");
        }
    }

    public void validarNomeCompleto() {
        if (this.nomeCompleto == null || this.nomeCompleto.trim().isEmpty()) {
            throw new DomainException("Nome completo é obrigatório.");
        }
    }

    public void validarCadastro() {
        validarNomeCompleto();
        validarCpf();
        validarMaioridade();
        validarEmail();
        validarTelefone();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
