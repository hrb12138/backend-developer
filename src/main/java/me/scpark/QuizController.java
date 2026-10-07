package me.scpark;


import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    // /quiz라는 요청이 GET 방식으로 왔을 때 quiz 메서드 실행
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam("code") int code){
        switch (code){
            case 1:
                return ResponseEntity.created(null).body("Created");
            case 2:
                return ResponseEntity.badRequest().body("Bad Request");
            default:
                return ResponseEntity.ok().body("OK");
        }
    }
    // /quiz라는 요청이 POST 방식으로 왔을 때 quiz2 메서드 실행
    @PostMapping("/quiz")
    public ResponseEntity<String> quiz2(@RequestBody Code code){
//        System.out.println("!!!!!!!!!!!!!!!!!!{"+code.value()+"}");
        switch(code.value()){
            case 1:
                return ResponseEntity.status(403).body("Forbidden");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }
}

record Code(int value){

}