package com.asusoftware.event_api.model.dto;

import com.asusoftware.event_api.model.Invitation;
import com.asusoftware.event_api.model.InvitationStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class InvitationDto {

    private UUID id;
    private UUID eventId;
    private String email;
    private InvitationStatus status;

    public static InvitationDto toDto(Invitation invitation) {
        InvitationDto invitationDto = new InvitationDto();
        invitationDto.setId(invitation.getId());
        invitationDto.setEmail(invitationDto.getEmail());
        invitationDto.setStatus(invitation.getStatus());
        invitationDto.setEventId(invitation.getEvent().getId());
        return invitationDto;
    }

}
