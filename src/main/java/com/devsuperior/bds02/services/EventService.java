package com.devsuperior.bds02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CityRepository cityRepository;

    public EventService(EventRepository eventRepository, CityRepository cityRepository) {
        this.eventRepository = eventRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        Event entity = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event id " + id + " not found"));
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City id " + dto.getCityId() + " not found"));
        entity.setCity(city);
        entity = eventRepository.save(entity);
        return new EventDTO(entity);
    }
}
