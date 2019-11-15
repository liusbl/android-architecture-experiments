1. Easy to forget to write something
2. Easy to misplace something
3. Easy to mix up something
4. Many
5. Allows for mistakes (such as non-safe types)
6. Common use cases should be easy to handle,
unusual ones should have non-hack way to implement

7.  Instead of:
  createPresenterEventMachine(Unit, {
                observe {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, Iteration5Fragment())
                        .commit()
                }
            }),

            I mistakenly wrote:
            createPresenterEventMachine(Unit, {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.container, Iteration5Fragment())
                                    .commit()
                        }),

 And had a hard time figuring out what was happening.
 So it's clear that proper creation methods/classes must be made.

 Maybe if you are creating a pusher then you should have observe method by default?

 8. Should provide easy way to integrate with popular libs.

 9. Too much bass classes

 10. Invocation order should matter as least as possible

 11. Minimize nullability

 12. Maybe these can be forgiven in deep internal implementations?

 13. What if you accidentally create text machine for the same field?

 14. Prevent nested observes somehow

 15. I don't agree that any thing you could think of is a valid criticism for an API.
        It should be discussed and the edge cases specified