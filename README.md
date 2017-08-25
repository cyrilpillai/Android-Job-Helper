# Android-Job-Helper

Evernote's [android-job](https://github.com/evernote/android-job) is an awesome library that helps running jobs in the background.

Anyone who has used this library in the past or is currently using it would agree to the fact that they have atleast for once scratched their heads for a while thinking why wasn't a particular job being scheduled when the code seemed perfectly fine. Then after banging their head on the keyboard for a while, they would realize that they forgot to add the job's `TAG` in the JobCreator implementation.

I have experienced the above pain quite a few times and one of the design rules of Unix motivated me to `[FIX]` this once and for all. 
</br>
##### Rule of Generation
> Developers should avoid writing code by hand and instead write abstract high-level programs that generate code. This rule aims to reduce human errors and save time.

This app demonstrates automating the unnecessary and error-prone part of creating and updating the JobCreator implementation by creating the class at compile time using Annotation Processing.

All we need to do is annotate the Job class with the `@IsJob` annotation and the annotation processor would take care of creating the JobCreator implementation.

```java
@IsJob
public class DemoSyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // run your job here
        return Result.SUCCESS;
    }
}
```

## License
```
Copyright 2017 Cyril Pillai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
