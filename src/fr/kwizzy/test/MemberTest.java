package fr.kwizzy.test;

import fr.kwizzy.waroflegions.clan.Member;
import fr.kwizzy.waroflegions.clan.Members;
import java.util.UUID;

/**
 * Par Alexis le 25/10/2016.
 */

public class MemberTest
{


    public void test()
    {
        Member member0 = new Member(false, false, true, 120, "jackos", "usi", UUID.randomUUID());
        Member member1 = new Member(true, false, true, 150, "minale", "usi", UUID.randomUUID());
        Member member2 = new Member(false, false, true, 140, "fermo", "usi", UUID.randomUUID());
        Member member3 = new Member(false, false, true, 0, "kaka", "usi", UUID.randomUUID());
        Members<Member> test = new Members<>(member0, member1, member2, member3);

        for (Member member : test.sort())
        {
            System.out.println("Member : " + member);
        }

//        List<Member> list = new ArrayList<>();
//        list.addAll(test);
//        JSONArray jo = new JSONArray(g.toJson(test));
//        System.out.println("Jo = " + jo);
//
//        ArrayList<Member> members = g.fromJson(jo.toString(), Member.class);
//        for (Member member : members)
//        {
//            System.out.println(member.toString());
//        }
////        String s = member0.toJson();
////        System.out.println("JsonMember = " + s);
////        JSONArray members = new JSONArray();
////        test.forEach(e -> members.put(new JSONObject(e.toJson())));
////        System.out.println("JsonArray = " + members);
////        JSONObject json = new JSONObject();
////        json.put("members", members);
////        JSONArray members1 = json.getJSONArray("members");
////        members1.forEach(e -> {
////            Member member = g.fromJson(e.toString(), Member.class);
////            System.out.println(member.toString());
////        });
    }


}
