package hexlet.code.service;


import hexlet.code.dto.labelDTO.LabelCreatedDTO;
import hexlet.code.dto.labelDTO.LabelDTO;
import hexlet.code.dto.labelDTO.LabelUpdatedDTO;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public List<LabelDTO> getAll() {
        List<Label> labels = labelRepository.findAll();
        return labels.stream().map(labelMapper::map).toList();
    }

    public LabelDTO show(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow();
        return labelMapper.map(label);
    }

    @Transactional
    public LabelDTO create(LabelCreatedDTO data) {
        Label label = labelMapper.map(data);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    @Transactional
    public LabelDTO update(LabelUpdatedDTO data, Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow();
        labelMapper.update(data, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    public void destroy(Long id) {
        labelRepository.deleteById(id);
    }
}
