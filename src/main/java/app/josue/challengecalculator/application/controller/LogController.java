package app.josue.challengecalculator.application.controller;

import app.josue.challengecalculator.application.dto.response.LogItemResponseDto;
import app.josue.challengecalculator.application.dto.response.LogResponseDto;
import app.josue.challengecalculator.domain.service.LogService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    public Mono<LogResponseDto> getLog(
            @RequestParam(value = "page", defaultValue = "0") int pageIndex,
            @RequestParam(value = "size", defaultValue = "10") int pageSize
    ) {
        return logService.getAll(PageRequest.of(pageIndex, pageSize))
                .map(page -> new LogResponseDto(
                        page.getContent().stream()
                                .map(model -> new LogItemResponseDto(
                                        model.id(),
                                        model.httpMethod(),
                                        model.path(),
                                        model.requestBody(),
                                        model.responseBody(),
                                        model.createdAt()
                                ))
                                .toList(),
                        page.getTotalPages(),
                        page.getTotalElements()
                ));
    }

}
