package com.example.trello;


import com.example.trello.domain.Board;
import com.example.trello.domain.Card;
import com.example.trello.domain.TList;
import com.example.trello.utils.TrelloCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.trello.TrelloUrl.DELETE_CARD;
import static com.example.trello.TrelloUrl.createUrlWithNoArgs;


@RestController
@RequestMapping("v2/trello")
public class TrelloController {

    public TrelloService trelloService = new TrelloService(new RestTemplateHttpClient(),"363ad4390c1ee58419a6b5a3aabcff3f","617dc66bdd69663192e27bdfbdadfc50c176c6f9b601b4048cd82bed458881c8");
    public Card card1 = new Card("Continuez Trello","Finir le backend de Trello RestAPI");

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public List <TList> fetchTrelloBoards() throws IOException {
        Board board = trelloService.getBoard("6220dee6e58fd671deefe515");
        return board.fetchLists();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/boardcards")
    public List <Card> fetchTrelloCards() throws IOException {
        List<Card> card = trelloService.getBoardCards("6220dee6e58fd671deefe515");
        return card;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/addcards/{ListId}")
    public void addCard(@RequestBody Card card, @PathVariable("ListId")String listID) throws IOException{
      trelloService.createCard("6220dee6e58fd671deefe515",card, listID);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/allboards")
    public List <Board> fetchALLTrelloBoards() throws IOException {
        List<Board> board = trelloService.getMemberBoards("me");
        return board;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alllists")
    public List <TList> fetchALLTrelloLists() throws IOException {
        List<TList> list = trelloService.getBoardLists("6220dee6e58fd671deefe515");
        return list;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cards/{listId}")
    public List<Card> fetchCardByList(@PathVariable("listId") String listId) throws IOException {
        List<Card> cards = trelloService.getListCards(listId);
        return cards; //lF8yZ3Lo
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/deletecard/{cardId}")
    public void deletingcard(@PathVariable("cardId") String cardId) throws IOException{
        trelloService.deleteCard(cardId);
        System.out.println("Card is deleted");
    }

    @RequestMapping(method=RequestMethod.PUT, value="/updateCard/{cardId}")
    public void updatingcard(@RequestBody Card card,@PathVariable("cardId") String cardId) throws IOException{
        trelloService.updateCard(card, cardId);
        System.out.println("Card is updated");
    }

    


}
