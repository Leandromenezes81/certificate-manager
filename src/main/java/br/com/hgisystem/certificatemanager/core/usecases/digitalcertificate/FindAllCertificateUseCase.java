package br.com.hgisystem.certificatemanager.core.usecases.digitalcertificate;

import br.com.hgisystem.certificatemanager.core.dataprovider.repository.CertificateRepository;
import br.com.hgisystem.certificatemanager.core.model.DigitalCertificate;
import br.com.hgisystem.certificatemanager.core.model.PaginationBase;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@Transactional(rollbackOn = Throwable.class)
public class FindAllCertificateUseCase {
    @Inject
    private CertificateRepository certificateRepository;

    public PaginationBase<Object> findAll(int page, int pageSize) {
        int totalCertificates = Math.toIntExact(this.certificateRepository.countAll());
        int totalPages = (int) Math.ceil((double) totalCertificates / pageSize);

        int firstResult = page * pageSize;

        List<DigitalCertificate> certificateList = this.certificateRepository.findAll(firstResult, pageSize);

        return PaginationBase.builder()
                .totalRecords(String.valueOf(totalCertificates))
                .totalPages(String.valueOf(totalPages))
                .currentPage(String.valueOf(page))
                .pageSize(String.valueOf(certificateList.size()))
                .hasNextPage(page < totalPages)
                .hasPreviousPage(page > 1)
                .data(certificateList)
                .build();
    }
}
