package com.heyiamej.bootcamp.service;

import com.heyiamej.bootcamp.dto.mapper.AtividadeMapper;
import com.heyiamej.bootcamp.dto.mapper.ProfissaoMapper;
import com.heyiamej.bootcamp.dto.request.AtividadeDTO;
import com.heyiamej.bootcamp.dto.response.MessageResponseDTO;
import com.heyiamej.bootcamp.entity.Atividade;
import com.heyiamej.bootcamp.exception.AtividadeNaoEncontradaException;
import com.heyiamej.bootcamp.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtividadeService {
    private AtividadeRepository atividadeRepository;
    private AtividadeMapper atividadeMapper = AtividadeMapper.INSTANCE;

    @Autowired
    public AtividadeService(AtividadeRepository atividadeRepository) {
        this.atividadeRepository = atividadeRepository;
    }


    public MessageResponseDTO criarResposta(Atividade atividade){
        return MessageResponseDTO.builder().message("Atividade: "+atividade.getNome()+" salva com id: "+atividade.getId()).build();
    }

    // Criar Atividade
    public MessageResponseDTO createAtividade(AtividadeDTO atividadeDTO){
        Atividade atividade = atividadeMapper.toAtividade(atividadeDTO);
        Atividade atividadeSalva = atividadeRepository.save(atividade);
        return criarResposta(atividadeSalva);
    }

    // Ver Atividade
    public Atividade getAtividadeById(Long id) throws AtividadeNaoEncontradaException {
        Atividade atividadeSalva = verifyIfExists(id);
        return atividadeSalva;
    }

    // Ver Atividades
    public List<Atividade> getAllAtividade(){
        List<Atividade> atividadeList = atividadeRepository.findAll();
        return atividadeList;
    }

    public Atividade verifyIfExists(Long id) throws AtividadeNaoEncontradaException {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new AtividadeNaoEncontradaException(id));
    }

    // Atualizar Atividade
    public MessageResponseDTO updateAtividadeById(Long id, AtividadeDTO atividadeDTO) throws AtividadeNaoEncontradaException {
        verifyIfExists(id);
        Atividade atividade = atividadeMapper.toAtividade(atividadeDTO);
        Atividade atividadeSalva = atividadeRepository.save(atividade);
        return criarResposta(atividadeSalva);
    }


    // Deletar Atividade
    public void deleteAtividadeById(Long id) throws AtividadeNaoEncontradaException {
        verifyIfExists(id);
        atividadeRepository.deleteById(id);
    }
}
