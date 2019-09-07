# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `rails
# db:schema:load`. When creating a new database, `rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2019_09_07_224818) do

  create_table "resume_answer_likes", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.integer "resume_answer_id", null: false
    t.integer "user_id", null: false
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["resume_answer_id"], name: "index_resume_answer_likes_on_resume_answer_id"
    t.index ["resume_id"], name: "index_resume_answer_likes_on_resume_id"
    t.index ["user_id"], name: "index_resume_answer_likes_on_user_id"
  end

  create_table "resume_answers", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.integer "user_id", null: false
    t.text "content"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.integer "likes_count"
    t.index ["resume_id"], name: "index_resume_answers_on_resume_id"
    t.index ["user_id"], name: "index_resume_answers_on_user_id"
  end

  create_table "resume_educations", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.string "institution"
    t.string "faculty"
    t.date "begin_date"
    t.date "end_date"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["resume_id"], name: "index_resume_educations_on_resume_id"
  end

  create_table "resume_works", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.string "company"
    t.string "position"
    t.date "begin_date"
    t.date "end_date"
    t.string "description"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["resume_id"], name: "index_resume_works_on_resume_id"
  end

  create_table "resumes", force: :cascade do |t|
    t.string "state"
    t.string "name"
    t.integer "user_id", null: false
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.string "url"
    t.text "summary"
    t.text "skills_description"
    t.string "github_url"
    t.text "awards_description"
    t.string "english_fluency"
    t.index ["user_id"], name: "index_resumes_on_user_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "email"
    t.string "first_name"
    t.string "last_name"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.string "encrypted_password", default: "", null: false
    t.string "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer "sign_in_count", default: 0, null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string "current_sign_in_ip"
    t.string "last_sign_in_ip"
    t.string "confirmation_token"
    t.datetime "confirmed_at"
    t.datetime "confirmation_sent_at"
    t.string "unconfirmed_email"
    t.integer "failed_attempts", default: 0, null: false
    t.string "unlock_token"
    t.datetime "locked_at"
    t.index ["confirmation_token"], name: "index_users_on_confirmation_token", unique: true
    t.index ["email"], name: "index_users_on_email", unique: true
    t.index ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true
    t.index ["unlock_token"], name: "index_users_on_unlock_token", unique: true
  end

  create_table "versions", force: :cascade do |t|
    t.string "item_type", null: false
    t.integer "item_id", limit: 8, null: false
    t.string "event", null: false
    t.string "whodunnit"
    t.text "object", limit: 1073741823
    t.datetime "created_at"
    t.index ["item_type", "item_id"], name: "index_versions_on_item_type_and_item_id"
  end

  add_foreign_key "resume_answer_likes", "resume_answers"
  add_foreign_key "resume_answer_likes", "resumes"
  add_foreign_key "resume_answer_likes", "users"
  add_foreign_key "resume_answers", "resumes"
  add_foreign_key "resume_answers", "users"
  add_foreign_key "resume_educations", "resumes"
  add_foreign_key "resume_works", "resumes"
  add_foreign_key "resumes", "users"
end
