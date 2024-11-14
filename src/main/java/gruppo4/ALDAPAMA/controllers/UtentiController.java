package gruppo4.ALDAPAMA.controllers;

import gruppo4.ALDAPAMA.dto.NewUtenteDTO;
import gruppo4.ALDAPAMA.entities.Utente;
import gruppo4.ALDAPAMA.exceptions.BadRequestException;
import gruppo4.ALDAPAMA.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Page<Utente> getAllUtenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.utentiService.getAll(page, size, sortBy);
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Utente getUtente(@PathVariable long utenteId) {
        return this.utentiService.getById(utenteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente saveNewUtente(@RequestBody @Validated NewUtenteDTO newUtenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload dell'utente!");
        }

        return this.utentiService.saveNewUtente(newUtenteDTO);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente updateUtente(@PathVariable long utenteId, @RequestBody @Validated NewUtenteDTO newUtenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload dell'utente!");
        }

        return this.utentiService.updateUser(utenteId, newUtenteDTO);
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteUtente(@PathVariable long utenteId) {
        this.utentiService.deleteUtente(utenteId);
    }

    @PatchMapping("/{utenteId}/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente uploadAvatar(@PathVariable long utenteId, @RequestParam("avatar") MultipartFile file) {
        return this.utentiService.uploadAvatar(utenteId, file);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    public Utente updateProfile(
            @AuthenticationPrincipal Utente currentUser,
            @RequestBody @Validated NewUtenteDTO body,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException("Errori nel payload");
        }
        return utentiService.updateUser(currentUser.getId(), body);
    }

    @PatchMapping("/me/avatar")
    public Utente updateAvatar(
            @AuthenticationPrincipal Utente currentUser,
            @RequestParam("avatar") MultipartFile file) {
        return utentiService.uploadAvatar(currentUser.getId(), file);
    }
}
