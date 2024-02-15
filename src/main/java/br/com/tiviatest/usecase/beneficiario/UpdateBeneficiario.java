package br.com.tiviatest.usecase.beneficiario;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.repository.BeneficiarioRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateBeneficiario {

    private final BeneficiarioRepository repository;
    private final FindBeneficiario find;

    public UpdateBeneficiario(BeneficiarioRepository repository, FindBeneficiario find) {
        this.repository = repository;
        this.find = find;
    }

    public Beneficiario execute(Long id, Beneficiario newBeneficiarioData) {
        Beneficiario toUpdateBeneficiario = find.byId(id);


        if (newBeneficiarioData.getDataNascimento() != null) {
            LocalDateTime dateToUpdate = formatDate(validateForEmptyValue(newBeneficiarioData.getDataNascimento().toString(), toUpdateBeneficiario.getDataNascimento().toString()));
            toUpdateBeneficiario.setDataNascimento(dateToUpdate);
        }

        toUpdateBeneficiario.setNome(validateForEmptyValue(newBeneficiarioData.getNome(), toUpdateBeneficiario.getNome()));
        toUpdateBeneficiario.setTelefone(validateForEmptyValue(newBeneficiarioData.getTelefone(), toUpdateBeneficiario.getTelefone()));

        return this.repository.update(toUpdateBeneficiario);
    }


    private String validateForEmptyValue(String newValue, String currentValue) {
        return newValue != null && !newValue.trim().isEmpty() ? newValue : currentValue;
    }

    private LocalDateTime formatDate(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(dateTime, formatter);
    }


}
